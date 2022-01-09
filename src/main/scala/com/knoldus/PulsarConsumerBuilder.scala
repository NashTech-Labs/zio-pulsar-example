package com.knoldus

import org.apache.pulsar.client.api.{
  ConsumerBuilder,
  PulsarClientException,
  Schema,
  SubscriptionMode,
  SubscriptionType
}
import zio.*

class PulsarConsumerBuilder[T: Tag](unsafeConsumerBuilder: ConsumerBuilder[T]):

  def withTopic(topic: String) = new PulsarConsumerBuilder(unsafeConsumerBuilder.topic(topic))

  def withSubType(subType: SubscriptionType) =
    new PulsarConsumerBuilder(unsafeConsumerBuilder.subscriptionType(subType))

  def withSubMode(mode: SubscriptionMode) = new PulsarConsumerBuilder(unsafeConsumerBuilder.subscriptionMode(mode))

  def withSubName(name: String) = new PulsarConsumerBuilder(unsafeConsumerBuilder.subscriptionName(name))

  def build(schema: Schema[T]): ZManaged[Any, PulsarClientException, PulsarConsumer[T]] =
    val consumer: IO[PulsarClientException, PulsarConsumer[T]] = ZIO
      .effect {
        new PulsarConsumer(unsafeConsumerBuilder.subscribe())
      }
      .refineToOrDie[PulsarClientException]
    ZManaged.make(consumer)(c => c.close.orDie)

end PulsarConsumerBuilder

object PulsarConsumerBuilder:
  def create[T: Tag](schema: Schema[T]): ZIO[Has[PulsarClient], PulsarClientException, PulsarConsumerBuilder[T]] =
    ZIO.accessM[Has[PulsarClient]](_.get.client).map(c => new PulsarConsumerBuilder[T](c.newConsumer(schema)))
end PulsarConsumerBuilder

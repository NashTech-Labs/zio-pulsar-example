package com.knoldus

import org.apache.pulsar.client.api.{Message, Schema, SubscriptionMode, SubscriptionType}
import zio.*
import zio.console.*

object Runner extends App {

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = consumeApp.useNow.orDie.exitCode

  val pulsarClient: ZLayer[Any, Nothing, Has[PulsarClient]] = PulsarClient.live("pulsar://localhost:6650")

  val schema = Schema.STRING

  val consumeApp = (for{
    consumeBuilder <- PulsarConsumerBuilder.create[String](schema).toManaged_
    consumer: PulsarConsumer[String] <- consumeBuilder
      .withTopic("topic-t1")
      .withSubType(SubscriptionType.Exclusive)
      .withSubMode(SubscriptionMode.Durable)
      .withSubName("subZ")
      .build(schema)
    message: Message[String] <- consumer.consume.toManaged_
    _ <- putStrLn(message.getValue).toManaged_.orDie
  } yield()).provideCustomLayer(pulsarClient)

}

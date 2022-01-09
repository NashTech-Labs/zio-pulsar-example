package com.knoldus

import org.apache.pulsar.client.api.{ Consumer, Message, PulsarClientException }
import zio.*

class PulsarConsumer[T](unsafeConsumer: Consumer[T]):

  def consume: IO[PulsarClientException, Message[T]] =
    ZIO.fromCompletableFuture(this.unsafeConsumer.receiveAsync()).refineToOrDie[PulsarClientException]

  def close: IO[PulsarClientException, Unit] =
    ZIO.fromCompletableFuture(this.unsafeConsumer.closeAsync()).unit.refineToOrDie[PulsarClientException]

end PulsarConsumer

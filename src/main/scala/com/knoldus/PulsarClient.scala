package com.knoldus

import org.apache.pulsar.client.api.{ClientBuilder, PulsarClientException, PulsarClient as JavaPulsarClient}
import zio.*

/**
 * PulsarClient -> PulsarConsumer -> Consuming the data from queue of pulsar
 * Pulsar Setup - I will be using standalone pulsar 2.9.1 using docker
 */
trait PulsarClient:
  def client: IO[PulsarClientException, JavaPulsarClient]
end PulsarClient

object PulsarClient:
  def live(serviceUrl: String): ZLayer[Any, Nothing, Has[PulsarClient]] =
    val client: PulsarClient = new PulsarClient {
      override def client: IO[PulsarClientException, JavaPulsarClient] =
        IO(JavaPulsarClient.builder().serviceUrl(serviceUrl).build()).refineToOrDie[PulsarClientException]
    }
    ZManaged.make(ZIO.effectTotal(client))(c => c.client.map(_.close()).orDie).toLayer
  end live
end PulsarClient

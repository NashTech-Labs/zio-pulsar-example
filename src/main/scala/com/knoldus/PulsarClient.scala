package com.knoldus

import org.apache.pulsar.client.api.{ ClientBuilder, PulsarClient as JavaPulsarClient, PulsarClientException }
import zio.*

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

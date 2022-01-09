# zio-pulsar-example
**_v0.1_**
___
[![CircleCI](https://circleci.com/gh/knoldus/zio-pulsar-example/tree/main.svg?style=svg)](https://circleci.com/gh/knoldus/zio-pulsar-example/tree/main)

Techhub template demonstrating the use of Pulsar consumer using ZIO 1.x.

#Steps to Run

## 1. Start Apache Pulsar Server

To start the server we will be using docker:

```bash
$ docker run -it -p 6650:6650  -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf apachepulsar/pulsar:2.9.1 bin/pulsar standalone
```
If you want to run Pulsar server locally or using Kubernetes follow the documentation:

https://pulsar.apache.org/docs/en/standalone/

## 2. Produce the message in given topic
Follow the document to produce the message in relevant topic:

https://pulsar.apache.org/docs/en/reference-cli-tools/#produce

**_Note: In next update I will add Producer._**

## 3. Start the consumer
To start the application/consumer execute the following command

```sbt
sbt run
```
This command will start the runner which consume the single message and exit.

___
&copy; Knoldus Inc. 2022

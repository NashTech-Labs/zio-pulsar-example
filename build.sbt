val zioVersion    = "1.0.12"
val pulsarVersion = "2.9.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "zio-pulsar-example",
    version := "0.1",
    scalaVersion := "3.1.0",
    developers := List(
      Developer("PKOfficial", "Prabhat Kashyap", "prabhat.kashyap@knoldus.com", url("https://github.com/PKOfficial"))
    ),
    Test / fork := true,
    (Test / parallelExecution) := false,
    libraryDependencies ++= Seq(
      "dev.zio"          %% "zio"           % zioVersion,
      "dev.zio"          %% "zio-streams"   % zioVersion,
      "org.apache.pulsar" % "pulsar-client" % pulsarVersion
    )
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")

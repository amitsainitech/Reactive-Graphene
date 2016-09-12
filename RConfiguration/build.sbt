name := "RConfiguration"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe" % "config" % "1.3.0",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "org.apache.zookeeper" % "zookeeper" % "3.3.4" excludeAll(
    ExclusionRule(name = "jms"),
    ExclusionRule(name = "jmxtools"),
    ExclusionRule(name = "jmxri")),
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")


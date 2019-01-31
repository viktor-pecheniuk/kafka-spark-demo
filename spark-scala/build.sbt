name := "spark-scala-demo"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.3.0"

retrieveManaged := true

fork := true

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.1.1"
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.1.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.14"

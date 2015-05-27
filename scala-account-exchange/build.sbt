
organization := "com.wesovi"

name := "scala-account-exchange"

version := "0.0.1"

startYear := Some(2015)

scalaVersion := "2.11.6"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"


EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

unmanagedSourceDirectories in Test <<= (sourceDirectory){ src => src / "test" / "scala" :: Nil}

val sprayVersion = "1.3.3"

val sprayJsonVersion = "1.3.2"

val akkaVersion = "2.4-SNAPSHOT"

val scalaTestVersion="2.2.1"

val specs2Version = "2.3.13"

val junitVersion ="4.11"

val specs2EmbedMongoVersion ="0.7.0"

val reactivemongo = "0.10.5.0.AKKA23"





enablePlugins(JavaAppPackaging)

Revolver.settings

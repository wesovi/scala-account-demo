
organization := "com.wesovi"

name := "scala-account-api"

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


libraryDependencies ++= Seq(
	"com.typesafe.akka"   %% 	"akka-actor" 		% akkaVersion,
	"com.typesafe.akka"   %% 	"akka-remote" 		% akkaVersion,
	"com.typesafe.akka"   %% 	"akka-slf4j" 		% akkaVersion,
	"io.spray"            %%  	"spray-can"     	% sprayVersion,
	"io.spray"            %%  	"spray-routing" 	% sprayVersion,
  	"io.spray" 			  %% 	"spray-json" 		% sprayJsonVersion,
  	"io.spray"            %%  	"spray-testkit" 	% sprayVersion  	% "test",
  	"ch.qos.logback" 	  % 	"logback-classic" 	% "1.1.2"
)


enablePlugins(JavaAppPackaging)

Revolver.settings
name := """svc-speaker"""
organization := "com.svc.speaker"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "io.github.zamblauskas" %% "scala-csv-parser" % "0.13.1"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.svc.political.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.svc.political.binders._"

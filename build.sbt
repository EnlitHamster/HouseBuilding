// Project settings
name := "HouseBuilding"
version := "0.1"
scalaVersion := "2.13.1"

// Akka depencencies
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.5"                // Akka Actors
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"                     // Quality through types
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"            // Unit tests
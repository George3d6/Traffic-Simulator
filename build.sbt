scalaVersion := "2.12.1"

version := "0.1"

resolvers +=
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.4.14"


libraryDependencies +=
  "com.graphhopper" % "directions-api-java-client" % "0.8.2.1"

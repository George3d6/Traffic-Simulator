scalaVersion := "2.12.1"

version := "0.1"

resolvers +=
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers +=
  "Sonatype OSS Snapshots" at "http://download.osgeo.org/webdav/geotools/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.4.14"


libraryDependencies +=
  "com.graphhopper" % "directions-api-java-client" % "0.8.2.1"

libraryDependencies +=
  "org.apache.kafka" % "kafka_2.11" % "0.10.1.0"

libraryDependencies +=
  "org.geotools" % "gt-epsg-hsql" % "16.0"

libraryDependencies +=
  "org.geotools" % "gt-api" % "16.0"

libraryDependencies += "joda-time" % "joda-time" % "2.9.6"
name := "akkainaction"
 
version := "1.0"
 
scalaVersion := "2.10.0"
 
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "OSS Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.1.0"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-testkit" % "2.1.0" % "test"

libraryDependencies +=
  "org.scalatest" %% "scalatest" % "2.0.M5b" % "test"



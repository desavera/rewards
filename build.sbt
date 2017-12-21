name := "rewards-api"

scalaVersion := "2.12.2"

version := "0.1"

val akkaVersion = "2.5.7"
val akkaHttpVersion = "10.0.11"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor"  % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
// If testkit used, explicitly declare dependency on akka-streams-testkit in same version as akka-actor
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit"   % akkaHttpVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion     % Test

assemblyMergeStrategy in assembly := {
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.first
  case PathList("org", "aopalliance", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", xs @ _*) => MergeStrategy.first
  case PathList("org", "objenesis", xs @ _*) => MergeStrategy.first
  case "parquet.thrift" => MergeStrategy.first
  case "plugin.xml" => MergeStrategy.first
  case x => {
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
  }
}

parallelExecution in Test := false

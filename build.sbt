lazy val dynamoDbTransactions =
  Project(
    id = "amazon-dynamodb-transactions",
    base = file(".")
  )
    .settings(
      organization := "com.thumbtack",
      version := "1.1.2",
      scalaVersion := "2.11.8",
      libraryDependencies ++= Seq(
        "com.amazonaws" % "aws-java-sdk" % "1.11.125",
        "org.mockito" % "mockito-core" % "1.9.5" % Test,
        // Let SBT run JUnit tests: https://github.com/sbt/junit-interface
        "com.novocode" % "junit-interface" % "0.11" % Test
      ),
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in packageDoc := false,
      sources in (Compile, doc) := Seq.empty,
      credentials += Credentials(file("sbt_repository_credentials.properties")),
      coursierUseSbtCredentials := true,
      updateOptions := updateOptions.value.withCachedResolution(true),
      publishTo := Some("Artifactory Realm" at "https://thumbtack.jfrog.io/thumbtack/maven-proxies"),
      // Build this artifact as a pure Java JAR.
      // https://xerial.org/blog/2014/03/24/sbt/
      autoScalaLibrary := false,
      crossPaths := false
    )

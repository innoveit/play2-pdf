name := "pdf-sample"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  javaWs,
  guice,
  "it.innove" % "play2-pdf" % "1.8.0-SNAPSHOT",
  "com.squareup.okhttp3" % "okhttp" % "3.9.1"
)


resolvers ++= Seq(
  "Sonatype snapshot" at "https://oss.sonatype.org/content/repositories/snapshots"
)
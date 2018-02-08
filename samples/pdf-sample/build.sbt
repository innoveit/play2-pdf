name := "pdf-sample"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  javaWs,
  guice,
  "it.innove" % "play2-pdf" % "1.7.0",
  "com.squareup.okhttp3" % "okhttp" % "3.5.0"
)


resolvers ++= Seq(
  "Sonatype snapshot" at "https://oss.sonatype.org/content/repositories/snapshots"
)
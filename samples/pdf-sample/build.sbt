name := "pdf-sample"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  guice,
  "it.innove" % "play2-pdf" % "1.6.0",
  "com.squareup.okhttp3" % "okhttp" % "3.5.0"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


resolvers ++= Seq(
  "Sonatype snapshot" at "https://oss.sonatype.org/content/repositories/snapshots"
)
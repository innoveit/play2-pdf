name := "play2-pdf"

organization := "it.innove"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

crossPaths := false

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.0.7",
  "nu.validator.htmlparser" % "htmlparser" % "1.4",
  "log4j" % "log4j" % "1.2.17"
)

resolvers ++= Seq(
  "Maven Central" at "http://http://repo1.maven.org/maven2/"
)

publishTo := Some(
  "Innove Repo" at "http://dev.in9.eu:8081/nexus/content/repositories/rel-innove/it/innove/"
)

credentials += Credentials(Path.userHome / ".ivy2/ivysettings.prop")

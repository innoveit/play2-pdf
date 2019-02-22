name := "play2-pdf"

organization := "it.innove"

version := "1.9.1"

lazy val root = (project in file(".")).enablePlugins(PlayMinimalJava)

scalaVersion := "2.12.8"

crossPaths := false

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.6",
  "org.xhtmlrenderer" % "flying-saucer-pdf-itext5" % "9.1.16",
  "nu.validator.htmlparser" % "htmlparser" % "1.4"
)

resolvers ++= Seq(
  "Maven Central" at "https://repo1.maven.org/maven2/"
)

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/innoveit/play2-pdf</url>
  <licenses>
    <license>
      <name>MIT license</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:innoveit/play2-pdf.git</url>
    <connection>scm:git:git@github.com:innoveit/play2-pdf.git</connection>
  </scm>
  <developers>
	<developer>
		<id>marcosinigaglia</id>
        <name>Marco Sinigaglia</name>
        <url>http://www.innove.it</url>
    </developer>
  </developers>
)

credentials += Credentials(Path.userHome / ".ivy2/sonatype.prop")

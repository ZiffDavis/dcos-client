val scala212 = Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfuture", // Turn on future language features.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

val scala211 = Seq(
  "-deprecation",
  "-feature",
  "-target:jvm-1.8",
  "-unchecked",
  "-encoding",
  "utf-8",
  // advanced
  "-Xcheckinit",
  "-Xfuture",
  "-Xlint:_",
  "-Xlog-reflective-calls",
  "-Xlog-free-terms",
  "-Xlog-free-types",
  "-Xmax-classfile-name",
  "130",
  "-Xverify",
  // private
  "-Ybreak-cycles",
  "-Yclosure-elim",
  "-Yconst-opt",
  "-Ydead-code",
  "-Ydelambdafy:method",
  "-Yinline",
  "-Yinline-handlers",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard",
  "-Ypartial-unification",
  //  "-Ymacro-debug-lite",
  ""
)

description in ThisBuild := "Mesosphere DC/OS API Client"

version in ThisBuild := "0.0.0"

startYear in ThisBuild := Some(2018)

organization in ThisBuild := "co.zdtc"

pomIncludeRepository in ThisBuild := { _ =>
  false
}

licenses in ThisBuild := Seq(
  "MIT" -> url("https://opensource.org/licenses/MIT"))

homepage in ThisBuild := Some(url("https://github.com/ZiffDavis/dcos-client"))

scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/ZiffDavis/dcos-client"),
    "scm:git@github.com:ZiffDavis/dcos-client.git"
  )
)

developers in ThisBuild := List(
  Developer(
    id = "chriskite",
    name = "Chris Kite",
    email = "chris_kite@ziffdavis.com",
    url = url("https://github.com/chriskite")
  )
)

publishMavenStyle in ThisBuild := true

publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

testOptions in Test in ThisBuild ++= Seq(
  Tests.Argument("-oD")
)

javacOptions in ThisBuild ++= Seq("-deprecation",
                                  "-g",
                                  "-source",
                                  "8",
                                  "-target",
                                  "8",
                                  "-Xlint")

name := "dcos-client"

version := "0.0.0"

scalaVersion := "2.12.5"

crossScalaVersions := Seq("2.11.12", "2.12.5")

scalacOptions ++= {
  if (scalaVersion.value startsWith "2.11.") {
    scala211
  } else {
    scala212
  }
}

val http4sVersion = "0.18.7"
val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion % "test",
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.9.3"
) ++ testDependencies

addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

organization  := "com.daikeshi"

name          := "weekly"

version       := "0.1"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

onLoadMessage := """
    |___      ___     / ___     //
    |  //  / /  / / //___) ) //___) ) //\ \     // //   / /
    |  //  / /  / / //       //       //  \ \   // ((___/ /
    |  ((__( (__/ / ((____   ((____   //    \ \ //      / /
  """.stripMargin

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  Seq(
    "postgresql"          %   "postgresql"         % "9.1-901-1.jdbc4",
    "org.joda"            %   "joda-convert"       % "1.7",
    "joda-time"           %   "joda-time"          % "2.7",
    "io.spray"            %%  "spray-can"          % sprayV,
    "io.spray"            %%  "spray-routing"      % sprayV,
    "io.spray"            %%  "spray-json"         % "1.3.1",
    "io.spray"            %%  "spray-client"       % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"         % akkaV,
    "org.json4s"          %%  "json4s-jackson"     % "3.2.11",
    "org.json4s"          %%  "json4s-ext"         % "3.2.11",
    "org.scalikejdbc"     %%  "scalikejdbc"        % "2.2.3",
    "org.scalikejdbc"     %%  "scalikejdbc-config" % "2.2.3",
    "io.spray"            %%  "spray-testkit"      % sprayV   % "test",
    "com.typesafe.akka"   %%  "akka-testkit"       % akkaV    % "test",
    "org.specs2"          %%  "specs2-core"        % "2.3.11" % "test",
    "org.scalikejdbc"     %%  "scalikejdbc-test"   % "2.2.3"  % "test"
  )
}

Revolver.settings

scalikejdbcSettings

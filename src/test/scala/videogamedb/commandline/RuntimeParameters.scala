package videogamedb.commandline

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParameters extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def USERCOUNT = System.getProperty("USERS", "5").toInt
  def RAMPDURATION = System.getProperty("RAMP_DURATION", "10").toInt
  def TESTDURATION = System.getProperty("TEST_DURATION", "30").toInt

  before {
    println(s"Running test with ${USERCOUNT} users")
    println(s"Ramping users over ${RAMPDURATION} seconds")
    println(s"Total test duration: ${TESTDURATION} seconds")
  }

  def getAllVideoGames() = {
    exec(
      http("Get all video games")
        .get("/videogame")
    ).pause(1)
  }

  val scn = scenario("Run from command line")
    .forever {
      exec(getAllVideoGames())
    }

  setUp(
    scn.inject(
      nothingFor(5),
      rampUsers(USERCOUNT).during(RAMPDURATION)
    )
  ).protocols(httpProtocol)
    .maxDuration(TESTDURATION)

}

package videogamedb.commandline

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParameters extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

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
      rampUsers(10).during(20)
    )
  ).protocols(httpProtocol)
    .maxDuration(20)

}

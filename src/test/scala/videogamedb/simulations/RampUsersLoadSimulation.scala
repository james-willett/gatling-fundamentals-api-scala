package videogamedb.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RampUsersLoadSimulation extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames() = {
    exec(
      http("Get all video games")
        .get("/videogame")
    )
  }

  def getSpecificGame() = {
    exec(
      http("Get specific game")
        .get("/videogame/2")
    )
  }

  val scn = scenario("Ramp Users Load Simulation")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificGame())
    .pause(5)
    .exec(getAllVideoGames())

  setUp(
    scn.inject(
      nothingFor(5),
      constantUsersPerSec(10).during(10),
      rampUsersPerSec(1).to(5).during(20)
    ).protocols(httpProtocol)
  )



}
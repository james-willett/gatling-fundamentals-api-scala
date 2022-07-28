package videogamedb.scriptfundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuse extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames() = {
    exec(http("Get all video games")
    .get("/videogame")
    .check(status.is(200)))
  }

  def getSpecificGame() = {
    exec(http("Get specific game")
    .get("/videogame/1")
    .check(status.in(200 to 210)))
  }

  val scn = scenario("Code resuse")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificGame())
    .pause(5)
    .exec(getAllVideoGames())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}

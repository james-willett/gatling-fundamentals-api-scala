package videogamedb

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyFirstTest extends Simulation{

  // 1 Http Configuration
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  // 2 Scenario Definition
  val scn = scenario("My First Test")
    .exec(http("Get all games")
    .get("/videogame"))


  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}

package videogamedb.feeders

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicCustomFeeder extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  var idNumbers = (1 to 10).iterator

  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

  def getSpecificVideoGame() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Get video game with id - #{gameId}")
        .get("/videogame/#{gameId}")
        .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Basic Custom Feeder")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}

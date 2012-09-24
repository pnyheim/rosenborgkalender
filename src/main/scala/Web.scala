import util.Properties
import unfiltered.jetty
import rbk.WelcomePlan
import rbk.MatchScraper

object Web {
  def main(args: Array[String]) {
    val matchscraper = new MatchScraper

    val port = Properties.envOrElse("PORT", "8080").toInt
    println("Starting on port:" + port)
    val http = jetty.Http(port)
    http.resources(getClass().getResource("/static"))
      .plan(new WelcomePlan(matchscraper))
    .run
  }

}
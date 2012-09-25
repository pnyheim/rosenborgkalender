import org.scalatest.FunSuite
import rbk.MatchScraper
class MatchScraperTest extends FunSuite {

  test("try to run matchscraper") {
    val matchScraper = new MatchScraper
    val list = matchScraper.scrapeMatches("2012")
    assert(list.size != 0);
  }

}
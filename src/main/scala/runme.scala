import rbk.MatchScraper
object runme extends App {
val matchScraper = new MatchScraper
val list = matchScraper.scrapeMatches("2012")

println(list.size);
}
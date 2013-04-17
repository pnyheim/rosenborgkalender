package rbk;
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDateTime
import org.jsoup.Connection.Method
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.collection.JavaConverters._
class MatchScraper {

  val dateTimeFormat = DateTimeFormat.forPattern("dd.MM.yyyy-HH:mm")

  def scrapeMatches(year: String): List[Match] = {
    val matchesResponse = Jsoup.connect("http://godfoten.rbk.no/terminliste.aspx?y=" + year)
    .method(Method.GET).timeout(25000).followRedirects(false).execute()

    val allRows = matchesResponse.parse.select("table#tblInfo > tbody > tr").listIterator.asScala
    
    val upcomingMatchesWithTimeDefined = allRows.filter {
      el => el.children().first().getElementsByClass("clsOppsettTableCell").size() != 0
//    }.filter {
//      el: Element => el.child(4).text.contains(":")
    }.map {
      el =>

        Match(
          el.child(0).text,
          el.child(1).text,
          parseTime(el.child(3).text,el.child(4).text),
          el.child(4).text,
          el.child(5).text)
    }
    upcomingMatchesWithTimeDefined.toList
  }

  def parseTime(date:String, time:String) = {
    var theTime = "23:59" 
    if (time.contains(":")) theTime = time
    dateTimeFormat.parseLocalDateTime(date + "-" + theTime)
  }
}

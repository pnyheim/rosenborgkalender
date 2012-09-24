package rbk;
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDateTime
import org.jsoup.Connection.Method
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.collection.JavaConverters._
class MatchScraper {

  val dateTimeFormat = DateTimeFormat.forPattern("dd.MM.yyyy-HH:mm")

  def scrapeMatches(): List[Match] = {
    val matchesResponse = Jsoup.connect("http://www.sponsorweb.no/terminliste.aspx?y=2012").method(Method.GET).timeout(25000).followRedirects(false).execute()

    val matchesDoc = matchesResponse.parse
    val matchesElements = matchesDoc.select("table#tblInfo > tbody > tr")
                                            .listIterator.asScala
//    println(matchesElements.size);
    val rows = matchesElements.filter{
      el: Element => el.children().first().getElementsByClass("clsOppsettTableCell").size() != 0
    } 
    val uprows = rows.filter{
      el: Element => el.child(4).text.contains(":")
    } 
//    println(rows.size)                                       
                                        
    val upcomingMatches = uprows.map {
      el: Element =>
       
        
        Match(
            el.child(0).text,
            el.child(1).text,            
            dateTimeFormat.parseLocalDateTime(el.child(3).text + "-" + el.child(4).text),
          el.child(4).text,
          el.child(5).text)
    }
//    println(upcomingMatches.size)
//    upcomingMatches.foreach{
//      ma:Match => println(ma.toString())
//    }
    upcomingMatches.toList
  }

}

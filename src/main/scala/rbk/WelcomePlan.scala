package rbk
import unfiltered.filter.Plan
import unfiltered.request.{Seg, Path, GET}
import unfiltered.response.{ResponseString, CharContentType, Html5, Ok}
import org.joda.time.{DateTimeZone, LocalDateTime}
import unfiltered.response.ResponseString
import calendar.VCalendar
import calendar.VCalendar
class WelcomePlan(val matchscraper:MatchScraper) extends Plan{

  def intent = {
     case GET(Path(Seg(Nil))) => Ok ~> Html5(<h1>Hei sveis</h1>)
     case GET(Path(Seg("matches" :: Nil))) => Ok ~> Html5(matchTable)
     case GET(Path(Seg("calendar" :: Nil))) => Ok ~>  CharContentType("text/calendar") ~> ResponseString(new VCalendar(matchscraper.scrapeMatches()).feed)

  }

  def matchTable = {
    val matchList = matchscraper.scrapeMatches()
    <h1>Rosenborg kamper</h1>
    <table>
      {
        matchList.map(s => <tr><td>{s.toString()}</td></tr>)
      }
    </table>
  
  }
}
package rbk
import unfiltered.filter.Plan
import unfiltered.request.{ Seg, Path, GET }
import unfiltered.response.{ ResponseString, CharContentType, Html5, Ok }
import org.joda.time.{ DateTimeZone, LocalDateTime }
import unfiltered.response.ResponseString
import calendar.VCalendar
import calendar.VCalendar
import unfiltered.response.Redirect
import unfiltered.response.Found
import unfiltered.response.Location
import unfiltered.response.SeeOther
import unfiltered.response.TemporaryRedirect
class WelcomePlan(val matchCache: MatchCache) extends Plan {
  def intent = {
    case GET(Path(Seg(Nil))) => Ok ~> Html5(<h1>Hei sveis</h1>)
    case GET(Path(Seg("matches" :: Nil))) => Ok ~> Html5(matchTable)
    case GET(Path(Seg("calendar" :: Nil))) => Ok ~> CharContentType("text/calendar") ~> ResponseString(new VCalendar(matchCache.upcomingMatchesFor("2012")).feed)
    case GET(Path(Seg("calendarPreview" :: Nil))) => Ok ~> CharContentType("text/plain") ~> ResponseString(new VCalendar(matchCache.upcomingMatchesFor("2012")).feed)
    case GET(Path(Seg("redirect" :: Nil))) => Ok ~> Html5(redirect)
  }

  def matchTable = {
    val matchList = matchCache.matchesFor("2012")

    <h1>Rosenborg kamper</h1>
    <table>
      {
        matchList.map(s => <tr><td>{ s.toString() }</td></tr>)
      }
    </table>
    <p>
      {
        matchCache.cache.stats().toString()
      }
    </p>
  }

  def redirect = {
    <head>
      <meta http-equiv="refresh" content="0;URL='/calendar'"/>     
    </head>
    <h1>testtesttesttesttesttesttesttest</h1>
  }

}
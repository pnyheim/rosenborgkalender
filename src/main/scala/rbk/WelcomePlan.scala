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
    case GET(Path(Seg(Nil))) => Found ~> Location("/matches")
    case GET(Path(Seg("matches" :: Nil))) => Ok ~> Html5(matchTable)
    case GET(Path(Seg("calendar.ics" :: Nil))) => Ok ~> CharContentType("text/calendar") ~> ResponseString(new VCalendar(matchCache.upcomingMatchesFor("2012")).feed)
    case GET(Path(Seg("calendarPreview" :: Nil))) => Ok ~> CharContentType("text/plain") ~> ResponseString(new VCalendar(matchCache.upcomingMatchesFor("2012")).feed)
    case GET(Path(Seg("calendar" :: Nil))) => Ok ~> Html5(redirect)
  }

  def matchTable = {
    val matchList = matchCache.matchesFor("2012")
    <head>
      {
        googleAnalytics
      }
    </head>
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
      <meta http-equiv="refresh" content="0;URL='/calendar.ics'"/>
      {
        googleAnalytics
      }
    </head>
    <h1>Rosenborg Kalender</h1>
      <p>Last ned her: <a href="/calendar.ics">calendar.ics</a></p>
      <p>Denne siden vil automatisk laste ned kalenderen</p>
  }

  def googleAnalytics = {
    <script type="text/javascript">
        {"""
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-35128737-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
"""}
      </script>
  }
}
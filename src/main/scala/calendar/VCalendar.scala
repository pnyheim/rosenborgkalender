package calendar

import rbk.Match
import org.joda.time._
import org.joda.time.DateTimeFieldType._

class VCalendar(assignedMatches: List[Match]) {

  val thisHour = LocalDateTime.now.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0)

  def this(assignedMatches: Match*) = this(assignedMatches.toList)

  private val calendarFormatString = "yyyyMMdd'T'HHmmss'Z"

  def feed = {
    assignedMatches.foldLeft(start)(_ + "\n" + vevent(_)) + "\n" + end
  }

  private def vevent(m: Match) = {
    val start = toUTC(m.date)
    val desc = """Kamp:  %s\nTurnering: %s""".format(m.teams, m.tournament)
    """BEGIN:VEVENT
    |DTSTART:%s
    |DTEND:%s
    |DTSTAMP:%s
    |LAST-MODIFIED:%s
    |UID:%s
    |LOCATION:%s
    |SUMMARY:%s
    |DESCRIPTION:%s
    |END:VEVENT""".stripMargin.format(
      start.toString(calendarFormatString),
      start.plusHours(2).toString(calendarFormatString),
      toUTC(thisHour).toString(calendarFormatString),
      toUTC(thisHour).toString(calendarFormatString),
      m.teams.replaceAll(" ", "_") + "_" + m.tournament.replaceAll(" ", "_") + "_" + m.date.getYear() + "@rosenborgkalender.herokuapp.com",
      m.tv,
      m.teams,
      desc)
  }

  val start =
    """BEGIN:VCALENDAR
    |VERSION:2.0
    |PRODID:-//rosenborgkalender/kamper//NONSGML v1.0//EN
    |METHOD:PUBLISH
    |X-WR-CALNAME:Rosenborgkalender
    |X-WR-CALDESC:Rosenborgkalender
    |X-PUBLISHED-TTL:PT24H""".stripMargin

  val end = "END:VCALENDAR"

  private def toUTC(dateTime: LocalDateTime) = {
    dateTime.toDateTime(DateTimeZone.forID("Europe/Oslo")).withZone(DateTimeZone.UTC).toLocalDateTime
  }

}

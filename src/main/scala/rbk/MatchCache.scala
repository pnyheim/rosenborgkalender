package rbk

import com.google.common.cache.{CacheBuilder, CacheLoader}
import java.util.concurrent.TimeUnit
import Scala2GuavaConversions.scalaFunction2GuavaFunction
import org.joda.time.LocalDateTime

class MatchCache(val matchscraper: MatchScraper) {

  val cache = CacheBuilder.newBuilder()
    .expireAfterWrite(24, TimeUnit.HOURS)
    .maximumSize(1000)
    .build(CacheLoader.from((year: String) => matchscraper.scrapeMatches(year)))

  def matchesFor(year:String): List[Match] = {
    cache.get(year)
  }
  
  def upcomingMatchesFor(year: String) = {
    matchesFor(year).filter(_.date.isAfter(LocalDateTime.now)).take(1)
  }
}


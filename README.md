# Rosenborgkalender

This is a small scala, unfiltered, jsoup hobby project that came to life after watching Morten Andersen-Gott (magott) present his dommer-fiks project at JavaZone 2012: "Fotballdommere fortjener vel også fritt nettleservalg" -> https://vimeo.com/49307943

## Goal
The goal of the project is to provide a stable calendar feed that auto-updates Rosenborg matches each season.
It does this by using jsoup to scrape the match table at http://www.rbk.no/fixtures-and-results and create a calendarfeed of the upcoming matches
This link can easily be added to e.g. Gmail calendar and will add events to it and update them automatically

## Conventions
Some matches do not yet have a starting time (the table at rbk.no says "Kommer") - in these cases, I schedule the match at 23:59 that day - and when I look at a match that starts 23:59, I know that is wrong and that the starttime is not yet known
The match table will be cached for 24 hours, and the calendar feed has X-PUBLISHED-TTL set to 24H

## Heroku
The application is deployed at heroku:

http://rosenborgkalender.herokuapp.com


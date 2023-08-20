package me.schnaidt.ics.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.schnaidt.ics.data.DataProvider;
import me.schnaidt.ics.data.PreSeasonService;
import me.schnaidt.ics.model.Club;
import me.schnaidt.ics.model.Game;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class CalendarService {

  private final CalendarInputService inputService;
  private final DataProvider dataProvider;
  private final PreSeasonService preSeasonService;
  private final CalendarOutputService outputService;


  private final Map<String, Club> clubMap = new HashMap<>();

  @SneakyThrows
  public void processCalendar() {

    clubMap.putAll(dataProvider.getDelClubs());
    clubMap.putAll(dataProvider.getChlClubs());
    clubMap.putAll(dataProvider.getPreSeasonClubs());


    Club adler = clubMap.get("Adler Mannheim");

    List<Game> gameList = inputService.readCalendar();
    log.info("calendar contains {} games", gameList.size());


    List<Game> games = gameList.stream()
        .peek(game -> {

          String cleanedSummary = game.getRawSummary().replace("SUMMARY:", "").trim();
          String[] summary = StringUtils.delimitedListToStringArray(cleanedSummary, " vs. ");

          Club homeTeam = findClub(summary[0].trim(), clubMap);
          game.setHomeTeam(homeTeam);
          Club guestTeam = findClub(summary[1].trim(), clubMap);
          game.setGuestTeam(guestTeam);

          if (null == homeTeam) {
            log.error("hometeam {} not found", summary[0].trim());
            throw new RuntimeException("kill kill, die die");
          }

          if (null == guestTeam) {
            log.error("guestTeam {} not found", summary[1].trim());
            throw new RuntimeException("kill kill, die die");
          }

          game.setMatch(homeTeam.club() + " - " + guestTeam.club());
          game.setLocation(homeTeam.location() + ", " + homeTeam.address());
          game.setTime(game.getRawTime());

          if (adler.equals(homeTeam)) {
            game.setHomeGame(true);
          }

          String cleanedTime = game.getRawTime().replace("DTSTART;TZID=Europe/Berlin;VALUE=DATE-TIME:", "");
          game.setRawTime(cleanedTime);

          log.debug(game.toString());

        })
        .map(preSeasonService::updateGameInformation)
        .toList();

    outputService.writeCalendar(games);
  }

  private Club findClub(String name, Map<String, Club> clubMap) {

    Club club = clubMap.get(name);

    if (null == club) {
      for (String current : clubMap.keySet()) {
        if (name.startsWith(current)) {
          club = clubMap.get(current);
        }
      }
    }

    return club;
  }

}

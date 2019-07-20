package me.schnaidt.ics.service;

import me.schnaidt.ics.config.ChlClubConfiguration;
import me.schnaidt.ics.config.DelClubConfiguration;
import me.schnaidt.ics.config.TestClubConfiguration;
import me.schnaidt.ics.model.Club;
import me.schnaidt.ics.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CalendarServiceImpl implements CalendarService {
test
  private final String SUMMARY = "SUMMARY:";

  @Autowired
  private DelClubConfiguration delClubConfiguration;

  @Autowired
  private ChlClubConfiguration chlClubConfiguration;

  @Autowired
  private TestClubConfiguration testClubConfiguration;

  private Predicate<String> isSummary() {
    return s -> s.startsWith(SUMMARY);
  }

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");


  @Override
  public void createCalendar() throws Exception {

    Map<String, Club> clubMap = new HashMap<>();
    delClubConfiguration.getClubs().forEach(c -> clubMap.put(c.getClub(), c));
    chlClubConfiguration.getClubs().forEach(c -> clubMap.put(c.getClub(), c));
    testClubConfiguration.getClubs().forEach(c -> clubMap.put(c.getClub(), c));


    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("2019-20.ics");
    Path path = Paths.get(url.toURI());


    Club adler = findClub("Adler Mannheim", clubMap);

    List<String> lines = Files.readAllLines(path);

    List<Game> games = lines.stream()
        .skip(30)
        .filter(isSummary())
        .map(s -> s.replace(SUMMARY, ""))
        .map(line -> StringUtils.delimitedListToStringArray(line, " vs. "))
        .map(a -> {
          Game g = new Game();

          Club homeTeam = findClub(a[0].trim(), clubMap);
          Club guestTeam = findClub(a[1].trim(), clubMap);

          if (null == homeTeam) {
            throw new RuntimeException("kill kill, die die");
          }

          if (null == guestTeam) {
            throw new RuntimeException("kill kill, die die");
          }

          g.setMatch(createMatch(homeTeam, guestTeam));

          g.setDescription(createDescription(homeTeam, guestTeam));


          String place = getDifferentPlace(homeTeam.getClub(), guestTeam.getClub());
          if (!StringUtils.hasText(place)) {
            place = homeTeam.getAddress();
          }
          g.setPlace(place);


          if (adler.equals(homeTeam)) {
            g.setHomeGame(true);
          }

          return g;

        }).collect(Collectors.toList());

    List<String> startTimes = lines.stream()
        .skip(30)
        .filter(s -> s.startsWith("DTSTART;TZID="))
        .collect(Collectors.toList());

    if (games.size() != startTimes.size()) {
      System.out.println("Foo");
      return;
    }

    for (int i = 0; i < games.size(); i++) {
      games.get(i).setTime(startTimes.get(i));
    }

    //  games.forEach(System.out::println);

    List<String> calendar = new ArrayList<>();
    calendar.addAll(createNewCalendar());
    calendar.add("");

    games.forEach(g -> {
      List<String> event = createEvent(g);
      calendar.addAll(event);
      calendar.add("");
    });

    calendar.add("END:VCALENDAR");

    Path out = Paths.get("out/my.ics");

    BufferedWriter writer = Files.newBufferedWriter(out);

    calendar.forEach(line -> {
      try {
        writer.write(line);
        writer.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    writer.flush();
    writer.close();
  }


  private String createMatch(Club homeTeam, Club guestTeam) {

    return homeTeam.getClub() + " - " + guestTeam.getClub();
  }


  private String createDescription(Club homeTeam, Club guestTeam) {

    if (testClubConfiguration.getClubs().contains(homeTeam) || testClubConfiguration.getClubs().contains(guestTeam)) {
      return "Testspiel";
    }

    if (chlClubConfiguration.getClubs().contains(homeTeam) || chlClubConfiguration.getClubs().contains(guestTeam)) {
      return "CHL Gruppenphase";
    }

    return "";
  }

  private String getDifferentPlace(String homeTeam, String guestTeam) {

    if ("Adler Mannheim".equals(homeTeam) && "HV71 Jönköping".equals(guestTeam)) {
      return "SAP Arena Mannheim, Nebenhalle Süd";
    }

    if ("Adler Mannheim".equals(homeTeam) && "HC Sparta Prag".equals(guestTeam)) {
      return "SAP Arena Mannheim, Nebenhalle Süd";
    }

    if ("Adler Mannheim".equals(homeTeam) && "HC Servette Genève".equals(guestTeam)) {
      return "Kolbenschmidt Arena Heilbronn";
    }

    return "";
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


  private List<String> createEvent(Game game) {

    List<String> list = new ArrayList<>();


    LocalDateTime date = LocalDateTime.now();
    String text = date.format(formatter);

    list.add("BEGIN:VEVENT");
    list.add("CLASS:PUBLIC");
    list.add("DTSTAMP:" + text);
    list.add("UID:" + UUID.randomUUID().toString());
    list.add("SUMMARY:" + game.getMatch());
    list.add(game.getTime());
    list.add("DURATION:PT3H");
    list.add("TRANSP:OPAQUE");
    list.add("LOCATION:" + game.getPlace());

    if (StringUtils.hasText(game.getDescription())) {
      list.add("DESCRIPTION:" + game.getDescription());
    }

    if (game.isHomeGame()) {
      list.add("X-MICROSOFT-CDO-BUSYSTATUS:BUSY");
      list.add("X-MICROSOFT-CDO-INTENDEDSTATUS:BUSY");
    } else {
      list.add("X-MICROSOFT-CDO-BUSYSTATUS:FREE");
      list.add("X-MICROSOFT-CDO-INTENDEDSTATUS:FREE");
    }

    list.add("X-MICROSOFT-CDO-IMPORTANCE:1");
    list.add("CATEGORIES:Eishockey");
    list.add("END:VEVENT");

    return list;
  }


  private List<String> createNewCalendar() {

    List<String> list = new ArrayList<>();

    list.add("BEGIN:VCALENDAR");
    list.add("VERSION:2.0");
    list.add("PRODID:-//Adler Mannheim//Spielplan//DE");
    list.add("X-WR-CALNAME:Adler Mannheim Spielplan");

    return list;
  }

}

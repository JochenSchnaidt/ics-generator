package me.schnaidt.ics.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.schnaidt.ics.model.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Service
public class CalendarOutputService {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

  @Value("${ics.file.out}")
  private String file;

  @Value("${ics.description}")
  private String description;

  public void writeCalendar(List<Game> games) throws Exception {

    List<String> calendar = new ArrayList<>(createNewCalendar());
    calendar.add("");

    games.forEach(g -> {
      List<String> event = createEvent(g);
      calendar.addAll(event);
      calendar.add("");
    });

    calendar.add("END:VCALENDAR");

    Path out = Paths.get(file);
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


  private List<String> createNewCalendar() {
    List<String> list = new ArrayList<>();
    list.add("BEGIN:VCALENDAR");
    list.add("VERSION:2.0");
    list.add("CALSCALE:GREGORIAN");
    list.add("PRODID:-//Adler Mannheim//Spielplan//DE");
    list.add("X-WR-CALNAME:Adler Mannheim Spielplan");
    list.add("X-WR-RELCALID:-//Adler Mannheim//Spielplan//DE");
    list.add("X-WR-CALDESC:" + description);
    list.add("X-WR-TIMEZONE:Europe/Berlin");
    list.add("BEGIN:VTIMEZONE");
    list.add("TZID:Europe/Berlin");
    list.add("X-LIC-LOCATION:Europe/Berlin");
    list.add("BEGIN:DAYLIGHT");
    list.add("TZOFFSETFROM:+0100");
    list.add("TZOFFSETTO:+0200");
    list.add("TZNAME:CEST");
    list.add("DTSTART:19700329T020000");
    list.add("RRULE:FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=3");
    list.add("END:DAYLIGHT");
    list.add("BEGIN:STANDARD");
    list.add("TZOFFSETFROM:+0200");
    list.add("TZOFFSETTO:+0100");
    list.add("TZNAME:CET");
    list.add("DTSTART:19701025T030000");
    list.add("RRULE:FREQ=YEARLY;INTERVAL=1;BYDAY=-1SU;BYMONTH=10");
    list.add("END:STANDARD");
    list.add("END:VTIMEZONE");


    return list;
  }


  private List<String> createEvent(Game game) {

    List<String> list = new ArrayList<>();

    LocalDateTime date = LocalDateTime.now();
    String text = date.format(formatter);

    list.add("BEGIN:VEVENT");
    list.add("CLASS:PUBLIC");
    list.add("DTSTAMP:" + text);
    list.add("UID:" + UUID.randomUUID());
    list.add("SUMMARY:" + game.getMatch());
    list.add(game.getTime());
    list.add("DURATION:PT3H");
    list.add("TRANSP:OPAQUE");
    list.add("LOCATION:" + game.getLocation());

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

}

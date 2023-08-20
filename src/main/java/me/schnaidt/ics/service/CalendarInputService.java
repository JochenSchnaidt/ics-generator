package me.schnaidt.ics.service;

import lombok.SneakyThrows;
import me.schnaidt.ics.model.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class CalendarInputService {

  private Predicate<String> isSummary() {
    return s -> s.startsWith("SUMMARY:");
  }

  private Predicate<String> isStartTimestamp() {
    return s -> s.startsWith("DTSTART");
  }

  @Value("${ics.file.in}")
  private String file;

  @SneakyThrows
  public List<Game> readCalendar() {

    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource(file);
    Path path = Paths.get(url.toURI());

    Game g = new Game();
    List<Game> gameList = new ArrayList<>();

    List<String> lines = Files.readAllLines(path);
    lines = lines.subList(30, lines.size() - 1);

    for (String line : lines) {
      if (line.startsWith("BEGIN:VEVENT")) {
        g = new Game();
        gameList.add(g);
      } else {
        if (isSummary().test(line)) {
          g.setRawSummary(line);
        }
        if (isStartTimestamp().test(line)) {
          g.setRawTime(line);
        }
      }
    }

    return gameList;
  }

}

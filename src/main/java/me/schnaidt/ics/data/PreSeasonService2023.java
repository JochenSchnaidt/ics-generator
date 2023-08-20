package me.schnaidt.ics.data;

import lombok.extern.slf4j.Slf4j;
import me.schnaidt.ics.model.Game;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Profile("2023")
@Service
public class PreSeasonService2023 implements PreSeasonService {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

  @Override
  public Game updateGameInformation(Game game) {

    LocalDate matchDay = LocalDateTime.parse(game.getRawTime(), formatter).toLocalDate();

    if (matchDay.equals(LocalDate.of(2023, 8, 17))) {
      game.setHomeGame(false);
      game.setDescription("Testspiel");
      game.setLocation("Sportpark Kitzbühel, Sportfeld 1, 6370 Kitzbühel, Österreich");
    }

    if (matchDay.equals(LocalDate.of(2023, 8, 20))) {
      game.setHomeGame(false);
      game.setDescription("Testspiel");
      game.setLocation("Sportpark Kitzbühel, Sportfeld 1, 6370 Kitzbühel, Österreich");
    }

    if (matchDay.equals(LocalDate.of(2023, 8, 25))) {
      game.setDescription("Testspiel");
    }

    if (matchDay.equals(LocalDate.of(2023, 8, 27))) {
      game.setDescription("Testspiel");
    }

    return game;
  }

}

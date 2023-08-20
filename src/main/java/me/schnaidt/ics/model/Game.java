package me.schnaidt.ics.model;

import lombok.Data;

@Data
public class Game {

  private Club homeTeam;
  private Club guestTeam;

  private String match;
  private String location;
  private String time;
  private String description;
  private boolean isHomeGame;


  private String rawSummary;
  private String rawTime;

}

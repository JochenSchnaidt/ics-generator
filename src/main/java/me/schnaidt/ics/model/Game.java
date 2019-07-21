package me.schnaidt.ics.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

  private String place;
  private String match;
  private String time;
  private String description;
  private boolean isHomeGame;

}

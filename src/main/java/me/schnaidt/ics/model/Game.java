package me.schnaidt.ics.model;

public class Game {

  private String place;
  private String match;
  private String time;

  private String description;

  private boolean isHomeGame;

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getMatch() {
    return match;
  }

  public void setMatch(String match) {
    this.match = match;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isHomeGame() {
    return isHomeGame;
  }

  public void setHomeGame(boolean homeGame) {
    isHomeGame = homeGame;
  }

}

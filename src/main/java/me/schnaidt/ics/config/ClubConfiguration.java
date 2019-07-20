package me.schnaidt.ics.config;

import me.schnaidt.ics.model.Club;

import java.util.ArrayList;
import java.util.List;

public abstract class ClubConfiguration {

  private List<Club> clubs = new ArrayList<>();

  public List<Club> getClubs() {
    return clubs;
  }

  public void setClubs(List<Club> clubs) {
    this.clubs = clubs;
  }

}

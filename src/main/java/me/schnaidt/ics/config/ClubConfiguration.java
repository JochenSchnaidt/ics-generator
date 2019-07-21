package me.schnaidt.ics.config;

import lombok.Getter;
import lombok.Setter;
import me.schnaidt.ics.model.Club;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ClubConfiguration {

  private List<Club> clubs = new ArrayList<>();

}

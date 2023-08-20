package me.schnaidt.ics.data;

import jakarta.annotation.PostConstruct;
import me.schnaidt.ics.model.Club;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataProvider {

  private final Map<String, Club> delClubs = new HashMap<>();
  private final Map<String, Club> chlClubs = new HashMap<>();
  private final Map<String, Club> preSeasonClubs = new HashMap<>();

  @PostConstruct
  public void init() {

    delClubs.put("Adler Mannheim"         , new Club("Adler Mannheim"         , "SAP Arena", "An der Arena 1, 68163 Mannheim"                             , "MAN"));
    delClubs.put("Augsburger Panther"     , new Club("Augsburger Panther"     , "Curt-Frenzel-Stadion", "Senkelbachstraße 2, 86153 Augsburg"              , "AEV"));
    delClubs.put("Eisbären Berlin"        , new Club("Eisbären Berlin"        , "Mercedes-Benz Arena", "Mercedes-Platz 1, 10243 Berlin"                   , "EBB"));
    delClubs.put("Fischtown Pinguins"     , new Club("Fischtown Pinguins"     , "Eisarena Bremerhaven", "Wilhelm-Kaisen-Platz 1, 27576 Bremerhaven"       , "BHV"));
    delClubs.put("Düsseldorfer EG"        , new Club("Düsseldorfer EG"        , "PSD Bank Dome", "Theodorstraße 281, 40472 Düsseldorf"                    , "DEG"));
    delClubs.put("Löwen Frankfurt"        , new Club("Löwen Frankfurt"        , "Eissporthalle Frankfurt", "Am Bornheimer Hang 4, 60386 Frankfurt am Main", "FRA"));
    delClubs.put("ERC Ingolstadt"         , new Club("ERC Ingolstadt"         , "Saturn Arena", "Südliche Ringstraße 64, 85053 Ingolstadt"                , "ING"));
    delClubs.put("Iserlohn Roosters"      , new Club("Iserlohn Roosters"      , "Eissporthalle am Seilersee", "Seeuferstraße 25, 58636 Iserlohn"          , "IEC"));
    delClubs.put("Kölner Haie"            , new Club("Kölner Haie"            , "Lanxess Arena", "Willy-Brandt-Platz 3, 50679 Köln"                       , "KEC"));
    delClubs.put("EHC Red Bull München"   , new Club("EHC Red Bull München"   , "Olympia-Eissportzentrum", "Spiridon-Louis-Ring 3, 80809 München"         , "RBM"));
    delClubs.put("Nürnberg Ice Tigers"    , new Club("Nürnberg Ice Tigers"    , "Arena Nürnberger Versicherung", "Kurt-Leucht-Weg 11, 90471 Nürnberg"     , "NIT"));
    delClubs.put("Schwenninger Wild Wings", new Club("Schwenninger Wild Wings", "Helios Arena", "Zum Mooswäldle, 78054 Villingen-Schwenningen"            , "SWW"));
    delClubs.put("Straubing Tigers"       , new Club("Straubing Tigers"       , "Eisstadion am Pulverturm", "Am Kinseherberg 23, 94315 Straubing"         , "STR"));
    delClubs.put("Grizzlys Wolfsburg"     , new Club("Grizzlys Wolfsburg"     , "Eis Arena Wolfsburg", "Allerpark 5, 38448 Wolfsburg"                     , "WOB"));

    chlClubs.put("Adler Mannheim"      , new Club("Adler Mannheim"      , "SAP Arena", "An der Arena 1, 68163 Mannheim"                                , "MAN"));
    chlClubs.put("HC Bozen"            , new Club("HC Bozen"            , "Sparkasse Arena", "Galvanistraße 34,39100 Bozen, Italien"                   , "HCB"));
    chlClubs.put("Red Bull Salzburg"   , new Club("EC Red Bull Salzburg", "Eisarena Salzburg", "Hermann-Bahr-Promenade 2, 5020 Salzburg, Österreich"   , "RBS"));
    chlClubs.put("Rouen Dragons"       , new Club("Rouen Dragons"       , "Patinoire de l'Île Lacroix", "Avenue Jacques-Chastellain, Rouen, Frankreich", "ROU"));
    chlClubs.put("Tappara"             , new Club("Tappara"             , "Nokia Arena", "Sorinkatu 6, 33100 Tampere, Finnland"                        , "TAP"));
    chlClubs.put("Lukko"               , new Club("Lukko"               , "Kivikylän Areena", "Nortamonkatu 23, 26100 Rauma, Finnland"                 , "LUK"));
    chlClubs.put("HC Košice"           , new Club("HC Košice"           , "Steel Aréna", "Nerudova 1627/12, 040 01 Košice, Slowakei"                   , "KOS"));

    preSeasonClubs.put("SC Bern (SUI)", new Club("SC Bern", "PostFinance-Arena", "Mingerstrasse 12, 3014 Bern, Schweiz", "SCB"));
  }

  public Map<String, Club> getDelClubs() {
    return Collections.unmodifiableMap(delClubs);
  }

  public Map<String, Club> getChlClubs() {
    return Collections.unmodifiableMap(chlClubs);
  }

  public Map<String, Club> getPreSeasonClubs() {
    return Collections.unmodifiableMap(preSeasonClubs);
  }
}

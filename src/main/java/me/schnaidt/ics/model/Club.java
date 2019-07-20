package me.schnaidt.ics.model;

public class Club {

  private String city;
  private String club;
  private String address;
  private String contraction;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getClub() {
    return club;
  }

  public void setClub(String club) {
    this.club = club;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContraction() {
    return contraction;
  }

  public void setContraction(String contraction) {
    this.contraction = contraction;
  }

  @Override
  public String toString() {
    return "Club{" +
        "city='" + city + '\'' +
        ", club='" + club + '\'' +
        ", address='" + address + '\'' +
        ", contraction='" + contraction + '\'' +
        '}';
  }

}

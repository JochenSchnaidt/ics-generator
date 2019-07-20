package me.schnaidt.ics.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("test")
public class TestClubConfiguration extends ClubConfiguration {

}

package me.schnaidt.ics.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("2023")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalendarServiceTest {

  @Autowired
  private CalendarService calendarService;

  @Test
  public void processCalendarTest() throws Exception {

    calendarService.processCalendar();
  }

}

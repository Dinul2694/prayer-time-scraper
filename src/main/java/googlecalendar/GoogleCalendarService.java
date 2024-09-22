package googlecalendar;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class GoogleCalendarService {
    private static final String CALENDAR_ID = "eb24fd84ddd742a50b316f44446b60c137f0a644e2a756500ae95b74d0a2e8f1@group.calendar.google.com";
    public static final String EUROPE_LONDON = "Europe/London";
    private Calendar calendarService;

    public GoogleCalendarService() {
        try {
            // Initialize the Google Calendar service using the updated GoogleAuth class
            calendarService = GoogleAuth.getCalendarService();
        } catch (GeneralSecurityException | IOException e) {
            log.error("Failed to initialize Google Calendar service: {}", e.getMessage());
        }
    }

    public void createEvent(String prayerName, String begins, String jamaat) {
        try {
            Event event = new Event()
                    .setSummary(prayerName + " Prayer")
                    .setDescription("Prayer time for " + prayerName);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Parse the start and end times for the event
            var startTime = LocalDate.now().atTime(LocalTime.parse(begins, timeFormatter));
            var endTime = LocalDate.now().atTime(LocalTime.parse(jamaat, timeFormatter));

            // Convert to the correct format for Google Calendar (ISO 8601 with timezone info)
            var startDateTime = startTime.atZone(ZoneId.of(EUROPE_LONDON)).toInstant();
            var endDateTime = endTime.atZone(ZoneId.of(EUROPE_LONDON)).toInstant();

            // Set event start and end times
            event.setStart(new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(startDateTime.toString()))
                    .setTimeZone(EUROPE_LONDON));
            event.setEnd(new EventDateTime()
                    .setDateTime(new com.google.api.client.util.DateTime(endDateTime.toString()))
                    .setTimeZone(EUROPE_LONDON));

            // Insert the event into the Google Calendar
            calendarService.events().insert(CALENDAR_ID, event).execute();
            log.info("Created Google Calendar event for {}.", prayerName);

        } catch (Exception e) {
            log.error("Error creating event for {}: {}", prayerName, e.getMessage());
        }
    }
}

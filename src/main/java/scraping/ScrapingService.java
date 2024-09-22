package scraping;

import googlecalendar.GoogleAuth;
import googlecalendar.GoogleCalendarService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class ScrapingService {
    private static final String URL = "https://www.masjidbilal.uk/";
    private final GoogleCalendarService calendarService = new GoogleCalendarService();

    public void scrapeAndSendToGoogleCalendar() {
        try {
            var document = fetchDocument(URL);
            var prayerTable = getPrayerTable(document);

            if (prayerTable.isPresent()) {
                var prayerData = prayerTable.get().select("tbody");
                cleanData(prayerData);
                log.info("Sending prayer data to Google Calendar...");
                sendPrayerDataToCalendar(prayerData);
            } else {
                log.error("Prayer table not found on the page.");
            }
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
        }
    }

    private void sendPrayerDataToCalendar(Elements prayerTableBody) {
        for (Element row : prayerTableBody.select("tr")) {
            String prayerName = row.select("th.prayerName").text();
            String begins = row.select("td.begins").text();
            String jamaat = row.select("td.jamah").text();

            if (!prayerName.isEmpty() && !begins.isEmpty()) {
                calendarService.createEvent(prayerName, begins, jamaat.isEmpty() ? begins : jamaat);
            }
        }
    }

    private void cleanData(Elements prayerTableBody) {
        log.info("Cleaning prayer table data...");
        prayerTableBody.select("tr").remove(0);
        prayerTableBody.select("tr").remove(0);
    }

    private Document fetchDocument(String url) throws IOException {
        log.info("Fetching document from URL: {}", url);
        return Jsoup.connect(url).get();
    }

    private Optional<Element> getPrayerTable(Document document) {
        log.info("Selecting prayer table from document...");
        return Optional.ofNullable(document.select("table").first());
    }
}

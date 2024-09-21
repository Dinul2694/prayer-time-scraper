package scraping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class ScrapingService {
    public static final String URL = "https://www.masjidbilal.uk/";
    public static void main(String[] args) throws IllegalArgumentException {
        try {
            // Select prayer table, assuming it is first table in tbody
            var prayerTableHtmlData = getPrayerTableFromMasjidBilal(Jsoup.connect(URL).get());
            if (prayerTableHtmlData.isPresent()){
                var prayerData = prayerTableHtmlData.get().select("tbody");
                cleanData(prayerData);
                // rows of standard prayers (elements)
                log.info("prayer table rows: \n{}", prayerData.select("tr"));
            } else {
                throw new IllegalArgumentException("Could not find any tables");
            }
        } catch (Exception e) {
            log.info("Error encountered, error message is: {}",e.getMessage());
        }

    }

    private static void cleanData(Elements prayerTableBody) {
        // removed first 2 rows as they were unnecessary
        prayerTableBody.select("tr").remove(0);
        prayerTableBody.select("tr").remove(0);
    }

    private static Optional<Element> getPrayerTableFromMasjidBilal(Document masjidBilalInfo) {
        return Optional.ofNullable(masjidBilalInfo.select("table").first());
    }

}

package jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;

@Slf4j
public class ScrapingService {
    public static final String URL = "https://www.masjidbilal.uk/";
    public static void main(String[] args) throws IllegalArgumentException {


        try {

            // Select prayer table, assuming it is first table in tbody
            var prayerTableHtml = getPrayerTableFromMasjidBilal(Jsoup.connect(URL).get());

            if (prayerTableHtml.isPresent()){
                var prayerTableBody = prayerTableHtml.get().select("tbody");

                // removed first 2 rows as they were unnecessary
                prayerTableBody.select("tr").remove(0);
                prayerTableBody.select("tr").remove(0);

                //segregated sunrise and jumuah rows (single element each)
                var sunriseRow = prayerTableBody.select("tr").remove(1);
                var jumuahRow = prayerTableBody.select("tr").remove(5);

                // rows of standard prayers (elements)
                var standardPrayerRows = prayerTableBody.select("tr");

                log.info("standard rows: \n{}",standardPrayerRows);
                log.info("sunrise row: \n{}",sunriseRow);
                log.info("jumuah row: \n{}",jumuahRow);
                log.info("testing");

            } else {
                throw new IllegalArgumentException("Could not find any tables");
            }


        } catch (Exception e) {
            log.info("Error, Jsoup could not connect to URL provided: {}",e.getMessage());
        }

    }

    private static Optional<Element> getPrayerTableFromMasjidBilal(Document masjidBilalInfo) {
        return Optional.ofNullable(masjidBilalInfo.select("table").first());
    }

}

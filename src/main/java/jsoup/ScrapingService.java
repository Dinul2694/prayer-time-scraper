package jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

                Map<String,Prayer>  prayersMap = new LinkedHashMap<>();

                for (int i = 2; i < prayerTableBody.select("tr").size(); i++) {
                    populatePrayerMap(prayersMap, prayerTableBody,i);
                }

                log.info("prayers: {}",prayersMap);

            } else {
                throw new IllegalArgumentException("Could not find any tables");
            }


        } catch (Exception e) {
            log.info("Error, Jsoup could not connect to URL provided: {}",e.getMessage());
        }

    }

    private static void populatePrayerMap(Map<String, Prayer> prayersMap, Elements prayerTableBody, int i) {
        var prayerTableRow = prayerTableBody.select("tr").get(i);
        var prayerName = prayerTableRow.select("th.prayerName").text();

        if (!prayerName.equals("Sunrise") && !prayerName.isEmpty()){
            var startTime = prayerTableRow.select("td.begins").text();
            var jamaatTime = prayerTableRow.select("td.jamah").text();
            Prayer prayer = new Prayer(prayerName, startTime, jamaatTime);
            prayersMap.put(prayerName,prayer);
        }

    }

    private static Optional<Element> getPrayerTableFromMasjidBilal(Document masjidBilalInfo) {
        return Optional.ofNullable(masjidBilalInfo.select("table").first());
    }

}

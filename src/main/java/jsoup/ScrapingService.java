package jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                Map<String,Prayer> prayersMap=null;
                var prayerTableBody = prayerTableHtml.get().select("tbody");

                for (int i = 2; i < prayerTableBody.size(); i++) {
                    prayersMap = populatePrayerMap(prayersMap,prayerTableBody);
                }

                log.info("prayers: {}",prayersMap);

            } else {
                throw new IllegalArgumentException("Could not find any tables");
            }


        } catch (Exception e) {
            log.info("Error, Jsoup could not connect to URL provided: {}",e.getMessage());
        }

    }

    private static Map<String, Prayer> populatePrayerMap(Map<String, Prayer> prayersMap, Elements prayerTableBody) {
        // implement popping prayer rows from table body and create prayer object
        var prayerTableRow = prayerTableBody.select("tr").remove(0);
        var prayerName = prayerTableRow.select("th.prayerName").text();

        if (!prayerName.equals("Sunrise") && !prayerName.equals("Jumuah")){
            var startTime = prayerTableRow.select("td.begins").text();
            var jamaatTime = prayerTableRow.select("td.jamah").text();
            Prayer prayer = new Prayer(prayerName, startTime, jamaatTime);
            prayersMap.put(prayerName,prayer);
        }

        return prayersMap;
    }

    private static Optional<Element> getPrayerTableFromMasjidBilal(Document masjidBilalInfo) {
        return Optional.ofNullable(masjidBilalInfo.select("table").first());
    }

}

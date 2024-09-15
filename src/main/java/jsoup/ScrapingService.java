package jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ScrapingService {
    public static void main(String[] args) throws IOException {
        String url = "https://www.masjidbilal.uk/";

        try {

            // Select prayer table, assuming it is first table
            var prayerTable = getPrayerTableFromMasjidBilal(Jsoup.connect(url).get());

            if (prayerTable.isPresent()){
                var prayerTableRows = prayerTable.get().select("tr");

                for(Element row: prayerTableRows){
                    // print out row data
                    Elements cols = row.select("th");

                }

            } else {
                throw new Exception("Could not find any tables");
            }


        } catch (Exception e) {
            log.info("Error, Jsoup could not connect to URL provided: {}",e.getMessage());
        }

    }

    private static Optional<Element> getPrayerTableFromMasjidBilal(Document masjidBilalInfo) {
        return Optional.ofNullable(masjidBilalInfo.select("table").first());
    }

}

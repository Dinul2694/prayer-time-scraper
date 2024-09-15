package jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
public class ScrapingService {
    public static void main(String[] args) throws IOException {
        String url = "https://www.masjidbilal.uk/";

        try {
            Document doc = Jsoup.connect(url).get();
            var prayerTimes = doc.select("table").first();
        } catch (IOException e) {
            log.info("Error, Jsoup could not connect to URL provided: {}",e.getMessage());
        }

    }

}

package org.dahmed.prayer_time_scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scraping.ScrapingService;

@SpringBootApplication
public class PrayerTimeScraperApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PrayerTimeScraperApplication.class, args);
		ScrapingService service = new ScrapingService();
		service.scrapeAndSendToGoogleCalendar();
	}

}

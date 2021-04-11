package com.noraster.scraper;

import com.noraster.csv.CsvWriter;
import com.noraster.scraper.pageobject.AdPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdsViewScraper extends AbstractScraper {

    private static final Logger logger = LoggerFactory.getLogger(AdsViewScraper.class);
    private final CsvWriter csvWriter;

    public AdsViewScraper(String outputFilePath) throws IOException {
        csvWriter = new CsvWriter(outputFilePath);
    }

    public void scrapeAds(List<String> urls) {
        logger.info("Starting to scrape {} ads", urls.size());
        List<Map<String, String>> ads = getAllAdData(urls);
        writeAdsToFile(ads);
        driver.close();
    }

    private List<Map<String, String>> getAllAdData(List<String> urls) {
        List<Map<String, String>> result = new ArrayList<>();
        for (String url : urls) {
            try {
                Map<String, String> ad = scrapeAd(driver, url);
                result.add(ad);
            } catch (Exception e) {
                logger.error("Failed to scrape ad: {}", url);
            }
        }
        return result;
    }

    private Map<String, String> scrapeAd(WebDriver driver, String url) {
        driver.navigate().to(url);
        AdPage adPage = new AdPage(driver);
        return getParameters(adPage);
    }

    private void writeAdsToFile(List<Map<String, String>> ads) {
        try {
            csvWriter.write(ads);
        } catch (IOException e) {
            logger.error("Failed to write ads to file", e);
            throw new RuntimeException("Failed to write ads to file", e);
        }
    }

    private Map<String, String> getParameters(AdPage adPage) {
        Map<String, String> parameters = new HashMap<>();

        parameters.putAll(adPage.getParameters());
        parameters.put("terület", adPage.getArea());
        parameters.put("ár", adPage.getPrice());
        parameters.put("cím", adPage.getAddress());
        parameters.put("szobák száma", adPage.getRoomCount());

        return parameters;
    }
}

package com.noraster;

import com.noraster.scraper.AdsViewScraper;
import com.noraster.scraper.ListViewScraper;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String URLS_FILE_PATH = "ad_urls.txt";
    private static final String ADS_FILE_PATH = "ad_data.csv";

    public static void main(String[] args) throws IOException {
        ListViewScraper listViewScraper = new ListViewScraper(URLS_FILE_PATH);
        List<String> urls = listViewScraper.scrapeAdUrls();

        AdsViewScraper scraper = new AdsViewScraper(ADS_FILE_PATH);
        scraper.scrapeAds(urls);
    }

}

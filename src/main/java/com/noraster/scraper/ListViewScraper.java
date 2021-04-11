package com.noraster.scraper;

import com.noraster.scraper.pageobject.ListPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListViewScraper extends AbstractScraper {

    private static final Logger logger = LoggerFactory.getLogger(ListViewScraper.class);
    private static final String LIST_URL_START_PAGE = "https://ingatlan.com/lista/elado+budapest+lakas";
    private static final String NEW_LINE_CHAR = "\n";
    private final FileWriter fileWriter;

    public ListViewScraper(String outputFilePath) throws IOException {
        fileWriter = new FileWriter(outputFilePath);
    }

    public List<String> scrapeAdUrls() throws IOException {
        driver.navigate().to(LIST_URL_START_PAGE);
        List<String> urls = scrapeAllPage();
        driver.close();
        fileWriter.close();
        return urls;
    }

    private List<String> scrapeAllPage() throws IOException {
        ListPage listPage = new ListPage(driver);
        int currentPage = 1;
        List<String> result = new ArrayList<>();
        while (listPage.hasNextPage()) {
            List<String> urls = scrapePage(listPage, currentPage);
            result.addAll(urls);
            listPage.goToNextPage();
        }
        return result;
    }

    private List<String> scrapePage(ListPage listPage, int currentPage) throws IOException {
        List<String> urls = listPage.getAdLinks();
        logger.info("Parsed page {}. Links found: {}", currentPage, urls.size());
        writeLinksToFile(urls);
        return urls;
    }

    private void writeLinksToFile(List<String> adLinks) throws IOException {
        String stringifiedLinks = String.join(NEW_LINE_CHAR, adLinks);
        fileWriter.append(stringifiedLinks);
        fileWriter.append(NEW_LINE_CHAR);
    }

}

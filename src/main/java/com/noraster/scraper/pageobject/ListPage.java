package com.noraster.scraper.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListPage {

    public static final By LISTING_LINK_SELECTOR = By.className("listing__link");
    public static final By NEXT_PAGE_SELECTOR = By.className("pagination__button");
    final WebDriver webDriver;

    public ListPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public List<String> getAdLinks() {
        List<WebElement> elements = webDriver.findElements(LISTING_LINK_SELECTOR);
        return elements.stream().map(e -> e.getAttribute("href")).collect(Collectors.toList());
    }

    public boolean hasNextPage() {
        return getNextPageButton().isPresent();
    }

    public void goToNextPage() {
        getNextPageButton()
                .orElseThrow(() -> new RuntimeException("Can't go to next page, there is no next page button found."))
                .click();
    }

    private Optional<WebElement> getNextPageButton() {
        return webDriver.findElements(NEXT_PAGE_SELECTOR).stream()
                .filter(e -> e.getText().toLowerCase().contains("következő")).findFirst();
    }

}

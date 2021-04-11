package com.noraster.scraper.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdPage {

    private static final By AREA_SIZE_SELECTOR = By.cssSelector(".parameter-area-size .parameter-value");
    private static final By ADDRESS_SELECTOR = By.cssSelector(".js-listing-title");
    private static final By PRICE_SELECTOR = By.cssSelector(".parameter-price .parameter-value");
    private static final By ROOM_COUNT_SELECTOR = By.cssSelector(".parameter-room .parameter-value");
    private static final By PARAMETERS_SELECTOR = By.cssSelector(".paramterers");
    final WebDriver webDriver;

    public AdPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getArea() {
        return webDriver.findElement(AREA_SIZE_SELECTOR).getText();
    }

    public String getAddress() {
        return webDriver.findElement(ADDRESS_SELECTOR).getText();
    }

    public String getPrice() {
        return webDriver.findElement(PRICE_SELECTOR).getText();
    }

    public String getRoomCount() {
        return webDriver.findElement(ROOM_COUNT_SELECTOR).getText();
    }

    public Map<String, String> getParameters() {
        WebElement parameterTable = webDriver.findElement(PARAMETERS_SELECTOR);
        List<WebElement> elements = parameterTable.findElements(By.tagName("tr"));
        Map<String, String> result = new HashMap<>();

        for (WebElement e : elements) {
            List<WebElement> cells = e.findElements(By.tagName("td"));
            String parameterName = cells.get(0).getText();
            String parameterValue = cells.get(1).getText();
            result.put(parameterName, parameterValue);
        }

        return result;
    }

}

package com.noraster.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public abstract class AbstractScraper {

    private final static String DEFAULT_GECKODRIVER_PATH = "F:\\Projects\\ingatlancom-scraper\\src\\main\\resources\\geckodriver.exe";
    protected final WebDriver driver;

    public AbstractScraper() {
        this.driver = getDefaultDriver();
    }

    public AbstractScraper(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver getDefaultDriver() {
        System.setProperty("webdriver.gecko.driver", DEFAULT_GECKODRIVER_PATH);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("javascript.enabled", false);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.setHeadless(true);

        return new FirefoxDriver(options);
    }
}

package org.example.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final FluentWait<WebDriver> wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);
    }
}

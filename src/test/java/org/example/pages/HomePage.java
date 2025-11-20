package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private static final String HOME_URL = "https://patimo.net/";
    private final By productsLink = By.cssSelector("nav.header__inline-menu a[href='/collections/all']");
    private final By acceptCookieButton = By.cssSelector("button[class*='cookie']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        driver.get(HOME_URL);
    }

    public void acceptCookiesIfPresent() {
        if (driver.findElements(acceptCookieButton).isEmpty()) {
            return;
        }
        WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookieButton));
        cookieButton.click();
    }

    public void clickProductsMenu() {
        WebElement products = wait.until(ExpectedConditions.elementToBeClickable(productsLink));
        products.click();
    }
}

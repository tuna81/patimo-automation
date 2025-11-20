package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private static final String HOME_URL = "https://patimo.net/";
    private final By productsLink = By.cssSelector("nav.header__inline-menu a[href='/collections/all']");
    private final By acceptCookieButton = By.cssSelector("button[class*='cookie']");
    private final By popupCloseButton = By.cssSelector(".popup-modal__close.promp-popup__close-btn");
    private final By popupOverlay = By.cssSelector(".sign-up-popup-overlay");

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

    public void closeNewsletterPopupIfPresent() {
        boolean overlayVisible = driver.findElements(popupOverlay).stream().anyMatch(WebElement::isDisplayed);
        boolean closeButtonVisible = driver.findElements(popupCloseButton).stream().anyMatch(WebElement::isDisplayed);
        if (!overlayVisible && !closeButtonVisible) {
            return;
        }
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton));
            closeBtn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupOverlay));
        } catch (Exception ignored) {
            driver.findElements(popupOverlay).stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .ifPresent(element -> ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].remove();", element));
        }
    }

    public void clickProductsMenu() {
        WebElement products = wait.until(ExpectedConditions.elementToBeClickable(productsLink));
        products.click();
    }
}

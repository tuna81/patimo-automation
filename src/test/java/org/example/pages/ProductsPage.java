package org.example.pages;

import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductsPage extends BasePage {
    private final By productLinks = By.cssSelector(".card__information a.full-unstyled-link[href*='/products/']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void openFirstProduct() {
        wait.until(driver -> driver.findElements(productLinks)
                .stream()
                .anyMatch(WebElement::isDisplayed));
        for (WebElement product : driver.findElements(productLinks)) {
            if (product.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(product)).click();
                return;
            }
        }
        throw new IllegalStateException("Gorunur urun karti bulunamadi");
    }

    public void openProductByName(String productName) {
        By knownLocator = getLocatorForKnownProduct(productName);
        if (knownLocator != null) {
            WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(knownLocator));
            clickWithScroll(product);
            return;
        }

        wait.until(driver -> !driver.findElements(productLinks).isEmpty());
        String expected = normalize(productName);
        for (WebElement product : driver.findElements(productLinks)) {
            String text = normalize(product.getText());
            if (!text.isEmpty() && text.contains(expected)) {
                clickWithScroll(product);
                return;
            }
        }
        throw new IllegalArgumentException("Urun bulunamadi: " + productName);
    }

    private void clickWithScroll(WebElement product) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);
    }

    private By getLocatorForKnownProduct(String productName) {
        String normalized = normalize(productName);
        if (normalized.contains("kedi kumu")) {
            return By.cssSelector(".card__information a.full-unstyled-link[href='/products/kokusuz-kedi-kumu-temizleme-kuregi']");
        }
        if (normalized.contains("buharlı") || normalized.contains("tuy toplama")) {
            return By.cssSelector(".card__information a.full-unstyled-link[href='/products/akilli-tuy-toplama-taragi']");
        }
        return null;
    }

    private String normalize(String value) {
        return value
                .replace("’", "'")
                .replace('\u00a0', ' ')
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }
}

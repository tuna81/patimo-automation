package org.example.pages;

import org.openqa.selenium.By;
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
}

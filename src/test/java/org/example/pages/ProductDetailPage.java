package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductDetailPage extends BasePage {
    private final By productTitle = By.cssSelector(".product__title h1");
    private final By addToCartButton = By.cssSelector("button[name='add']");
    private final By cartDrawerItems = By.cssSelector("cart-drawer-items");
    private final By cartDrawerContent = By.cssSelector("#CartDrawer-CartItems");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
        return titleElement.getText().trim();
    }

    public void addProductToCart() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        button.sendKeys(Keys.END);
        button.click();
    }

    public boolean waitForProductInCartDrawer(String productName) {
        return wait.until(driver -> {
            WebElement drawer = driver.findElement(cartDrawerItems);
            String classes = drawer.getAttribute("class");
            if (classes != null && classes.contains("is-empty")) {
                return false;
            }
            WebElement cartContent = driver.findElement(cartDrawerContent);
            return cartContent.getText().toLowerCase().contains(productName.toLowerCase());
        });
    }
}

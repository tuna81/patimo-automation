package org.example.stepdefinitions;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.DriverFactory;
import org.example.pages.HomePage;
import org.example.pages.ProductDetailPage;
import org.example.pages.ProductsPage;

public class PatimoSteps {
    private HomePage homePage;
    private ProductsPage productsPage;
    private ProductDetailPage productDetailPage;
    private String selectedProductName;

    @Given("kullanici Patimo ana sayfasini acar")
    public void kullaniciPatimoAnaSayfasiniAcar() {
        homePage = new HomePage(DriverFactory.getDriver());
        homePage.navigateToHomePage();
        homePage.acceptCookiesIfPresent();
        homePage.closeNewsletterPopupIfPresent();
    }

    @When("kullanici Urunler sayfasina gider")
    public void kullaniciUrunlerSayfasinaGider() {
        if (homePage == null) {
            homePage = new HomePage(DriverFactory.getDriver());
        }
        homePage.clickProductsMenu();
        productsPage = new ProductsPage(DriverFactory.getDriver());
    }

    @When("kullanici listelenen ilk urunu secer")
    public void kullaniciListelenenIlkUruneTikar() {
        if (productsPage == null) {
            productsPage = new ProductsPage(DriverFactory.getDriver());
        }
        productsPage.openFirstProduct();
        productDetailPage = new ProductDetailPage(DriverFactory.getDriver());
        selectedProductName = productDetailPage.getProductName();
    }

    @When("kullanici urunu sepete ekler")
    public void kullaniciUrunuSepeteEkler() {
        if (productDetailPage == null) {
            productDetailPage = new ProductDetailPage(DriverFactory.getDriver());
        }
        if (selectedProductName == null || selectedProductName.isEmpty()) {
            selectedProductName = productDetailPage.getProductName();
        }
        productDetailPage.addProductToCart();
    }

    @Then("sepet urunun basariyla eklendigini gosterir")
    public void sepetUrununBasariylaEklendiginiGosterir() {
        boolean productVisible = productDetailPage.waitForProductInCartDrawer(selectedProductName);
        assertTrue("Sepet cekmecesinde urun bulunamadi", productVisible);
    }
}

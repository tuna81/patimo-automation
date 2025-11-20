package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        if (DRIVER.get() == null) {
            
            ChromeOptions options = new ChromeOptions();
            
            // --- DOCKER vs LOCAL AYRIMI ---
            // Dockerfile'da CHROME_DRIVER ortam değişkenini tanımlamıştık (/usr/bin/chromedriver).
            String chromeDriverPath = System.getenv("CHROME_DRIVER");
            String chromeBinaryPath = System.getenv("CHROME_BIN");

            if (chromeDriverPath != null && !chromeDriverPath.isBlank()) {
                // 1. SENARYO: DOCKER ORTAMI
                // WebDriverManager KULLANMA! Hazır kurulu driver'ı göster.
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                
                if (chromeBinaryPath != null && !chromeBinaryPath.isBlank()) {
                    options.setBinary(chromeBinaryPath);
                }

                // Docker'da her zaman Headless olmak zorundayız.
                options.addArguments("--headless=new");
            } else {
                // 2. SENARYO: LOCAL (Kendi Bilgisayarın)
                // Burada WebDriverManager kullanabilirsin.
                WebDriverManager.chromedriver().setup();
                
                // Localde headless isteğe bağlı olsun
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    options.addArguments("--headless=new");
                }
            }

            // --- ORTAK AYARLAR (Hem Docker hem Local için kritik) ---
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage"); // Docker için hayati
            options.addArguments("--no-sandbox");            // Docker root kullanıcısı için hayati
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-software-rasterizer");

            // Driver'ı ayağa kaldır
            DRIVER.set(new ChromeDriver(options));
            
            // Bekleme süreleri
            DRIVER.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            DRIVER.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        }
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
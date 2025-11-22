# Patimo E2E Automation

Selenium + Cucumber + Java tabanlı basit bir uçtan uca test projesi. Senaryo patimo.net sitesini açıp "Ürünler" menüsüne gider, listedeki ilk ürünü seçer ve sepete ekler.

## Teknoloji yığını
- Java 11 (Maven derleme hedefi)
- Maven 3.9.x
- Selenium 4.38.0 + WebDriverManager
- Cucumber 7.18.1 (JUnit runner)
- Docker (Chromium kurulu koşu ortamı)
- Jenkins (pipeline ile CI)

## Yerel çalıştırma
```bash
mvn test
```
Varsayılan olarak headless çalışır. GUI görmek istersen `-Dheadless=false` bayrağını ekleyebilirsin.

## Docker ile
```bash
docker build -t patimo-automation .
docker run --rm --shm-size=2g patimo-automation
```
Chromium ve chromedriver Dockerfile içerisinde kuruludur. `-e headless=false` ile konteyner içinde ekran da açtırabilirsin (VNC vb. ile).

## Jenkins pipeline
Repo kökündeki `Jenkinsfile` üç aşamalı bir pipeline tanımlar:
1. Checkout
2. Docker imajını build et (`docker build -t patimo-automation .`)
3. Testleri konteynerda çalıştır (`docker run --rm --shm-size=2g -e CUCUMBER_FILTER_TAGS="${params.TAG_EXPRESSION}" patimo-automation`)

`TAG_EXPRESSION` parametresi varsayılan olarak `not @ignore` değerindedir ve Jenkins UI üzerinden değiştirilebilir (örn. sadece `@smoke` tag’li senaryoları koşmak için). Post adımı imajı siler. Jenkins container’ının host Docker soketine erişimi için `/var/run/docker.sock` ve Docker CLI bind edilmelidir.

### Cucumber tagleri
- `@cart`: Sepet akışı senaryoları (tüm senaryolar)
- `@smoke`: Hızlı kontrol (Kedi kumu küreği ürünü)
- `@regression`: Diğer ürün senaryosu

Filtreleme için: `mvn test -Dcucumber.filter.tags="@smoke"` veya Docker/Jenkins parametresi kullanılabilir.

## Yapı
- `src/test/resources/features`: Cucumber senaryoları
- `src/test/java/org/example/pages`: Page Object katmanı
- `src/test/java/org/example/stepdefinitions`: Step definition’lar
- `src/test/java/org/example/DriverFactory.java`: WebDriver yönetimi
- `Dockerfile`: Testleri çalıştıracak bağımsız ortam

## Notlar
- Proje Java 11 hedefler; Docker imajında OpenJDK 17 kullanıyoruz ancak `maven.compiler.source/target=11` olarak setli.
- `DriverFactory` Linux/Jenkins ortamları için `CHROME_BIN` ve `CHROME_DRIVER` env değişkenlerini okur.

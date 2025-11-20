Feature: Patimo sepet islemleri
  Patimo sitesinde bir kullanici urunler sayfasindan rastgele bir urun secip sepete ekleyebilmeli.

  Scenario: Kullanici urunu sepete ekler
    Given kullanici Patimo ana sayfasini acar
    When kullanici Urunler sayfasina gider
    And kullanici listelenen ilk urunu secer
    And kullanici urunu sepete ekler
    Then sepet urunun basariyla eklendigini gosterir

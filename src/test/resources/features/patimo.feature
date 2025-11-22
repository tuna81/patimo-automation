Feature: Patimo sepet islemleri
  Patimo sitesinde kullanici urunlerden birini secip sepete ekleyebilmeli.

  @cart
  Scenario Outline: Kullanici secilen urunu sepete ekler
    Given kullanici Patimo ana sayfasini acar
    When kullanici Urunler sayfasina gider
    And kullanici "<urunAdi>" urununu secer
    And kullanici urunu sepete ekler
    Then sepet urunun basariyla eklendigini gosterir

    @smoke
    Examples: Hizli kontrol urunu
      | urunAdi                        |
      | 2’si 1 Arada Kedi Kumu Küreği |

    @regression
    Examples: Diger urun
      | urunAdi                    |
      | Buharlı Tüy Toplama Tarağı |

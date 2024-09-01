package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.KitapYurduPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseBeforeMethodAfterMethod;

import java.util.List;

public class KitapYurduTest extends TestBaseBeforeMethodAfterMethod {

    @Test
    public void testKitapYurdu(){

        //KitapYurdu objesini tanımla
        KitapYurduPage kitapYurduPage = new KitapYurduPage();

        //Kitap Yurdu sayfasına git, çerezleri kabul et ve search box'a istenilen ifadeyi girip arama yap
        Driver.getDriver().get(ConfigReader.getProperty("kitapYurduURL"));
        kitapYurduPage.acceptCookies.click();
        kitapYurduPage.searchBox.sendKeys("Sabahattin Ali", Keys.ENTER);

        //!!Filtreleme butonuna tıklanır ve Alfabetik seçilir
        kitapYurduPage.selectFilterButton.click();

        // Actions objesini tanımla
        Actions actions = new Actions(Driver.getDriver());

        // Açılan menüden "Alfabetik" textine tıklanır
        for (WebElement option : kitapYurduPage.filterOptions) {
            if (option.getText().contains("Alfabetik")) {
                actions.moveToElement(option).click().perform();
                break;
            }
        }

        //Stokta olan seçeneği seçilir
        kitapYurduPage.inStockButton.click();

        //İkinci sayfaya gidilir
        kitapYurduPage.secondPage.click();

        //Üçüncü sıradaki kitap seçilir
        kitapYurduPage.selectThirdBook.click();

        //!!Ürün detay sayfasındaki fiyat okunur
        try {
            // Ürün fiyatını oku eğer okuyamazsan hata mesajı dön
            String priceText = kitapYurduPage.productPrice.getText();
            System.out.println("!!Ürün Detay Sayfasındaki Fiyat Kontrolü!!");
            System.out.println("Ürün fiyatı: " + priceText);
        } catch (Exception e) {
            System.out.println("Fiyat bilgisi bulunamadı. Hata: " + e.getMessage());
        }

        //Ürün sepete eklenir
        kitapYurduPage.addToCartButton.click();

        //!!Ürünün sepete eklendiği kontrol edilir

        try {
            //Sepet sayısının güncellenmesini bekle
            WebDriverWait wait = Driver.getWait();
            wait.until(ExpectedConditions.textToBe(By.id("cart-items"), "1"));

            // Sepetteki ürün sayısını al ve göster
            String cartItemsText = kitapYurduPage.cartItems.getText();
            System.out.println("!!Sepetteki Ürün Sayısı Kontrolü!!");
            System.out.println("Sepetteki ürün sayısı: " + cartItemsText);

            // Sepette 1 adet ürün olup olmadığını doğruladıktan sonra ilgili mesajı dön
            if ("1".equals(cartItemsText)) {
                System.out.println("Ürün sepete başarıyla eklendi!");
            } else {
                System.out.println("Ürün sepete eklenmedi veya sepet sayısı güncellenmedi!");
            }
        } catch (Exception e) {
            System.out.println("Sepet bilgisi okunamadı veya güncellenmedi! Hata: " + e.getMessage());
        }

        //Sepete gitmek için önce sepet detayını aç ve sepete git
        kitapYurduPage.showCartButton.click();
        kitapYurduPage.goToCartButton.click();

        //Ürün adedini 2 yapmak için default olarak gelen 1 rakamını sil ve yerine 2 yaz
        kitapYurduPage.quantityField.clear();
        kitapYurduPage.quantityField.sendKeys("2");
        kitapYurduPage.quantityField.sendKeys(Keys.ENTER);

        //!!Ürün fiyatının 2 katı ile toplam fiyatın eşit olduğu kontrol edilmeli!!

        // Ürün fiyatını ve toplam fiyatı almak için elementlerini tanımla
        WebElement unitPriceElement = kitapYurduPage.unitPrice;
        WebElement totalPriceElement = kitapYurduPage.totalPrice;

        // Sitedeki fiyatları al ve sayısal değerlere dönüştür "," yerine "." sembolüne çevir
        String unitPriceText = unitPriceElement.getText().replaceAll("[^0-9,]", "").trim().replace(",", ".");
        String totalPriceText = totalPriceElement.getText().replaceAll("[^0-9,]", "").trim().replace(",", ".");

        //double formatta tanımla
        double unitPrice;
        double totalPrice;

        //Fiyatlar doğru formatta alınmazsa testi durdur ve hata mesajı dön
        try {
            unitPrice = Double.parseDouble(unitPriceText);
            totalPrice = Double.parseDouble(totalPriceText);
        } catch (NumberFormatException e) {
            System.out.println("Fiyat formatı hatası: " + e.getMessage());
            return;
        }

        // Ürün fiyatının iki katı ile toplam fiyatı karşılaştırıp Assert ile eşitlik kontrolü yap ve terminale yazdır
        double expectedTotalPrice = unitPrice * 2;
        Assert.assertEquals(totalPrice, expectedTotalPrice, "Toplam fiyat, ürün fiyatının iki katı olmalı!");

        System.out.println("!!Birim Fiyat ve Toplam Fiyat Eşitlik Kontrolü!!");
        System.out.println("Ürün fiyatı: " + unitPrice);
        System.out.println("Toplam fiyat: " + totalPrice);
        System.out.println("Ürün fiyatının iki katı: " + expectedTotalPrice);

        //Sepetteki ürünler temizlenir
        kitapYurduPage.clearCartButton.click();

        //!!Sepetten ürünlerin temizlendiğini kontrol et

        try {
            //Sepet sayısının güncellenmesini bekle
            WebDriverWait wait = Driver.getWait();
            wait.until(ExpectedConditions.textToBe(By.id("cart-items"), "0"));

            // Sepetteki ürün sayısını al ve göster
            String cartItemsText = kitapYurduPage.cartItems.getText();
            System.out.println("!!Temizlenmiş Sepetteki Ürün Sayısı Kontrolü!!");
            System.out.println("Sepetteki ürün sayısı: " + cartItemsText);

            // Sepette 0 adet ürün olup olmadığını doğruladıktan sonra ilgili mesajı dön
            if ("0".equals(cartItemsText)) {
                System.out.println("Ürünler sepetten başarıyla temizlendi!");
            } else {
                System.out.println("Ürünler sepetten temizlenmedi veya sepet sayısı güncellenmedi!");
            }
        } catch (Exception e) {
            System.out.println("Sepet bilgisi okunamadı veya güncellenmedi! Hata: " + e.getMessage());
        }

    }

}

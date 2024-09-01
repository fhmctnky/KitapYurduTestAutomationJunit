package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class KitapYurduPage {

    public KitapYurduPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//div[@id='cookiescript_accept']")
    public WebElement acceptCookies;

    @FindBy(xpath = "//input[@id='search-input']")
    public WebElement searchBox;

    @FindBy(xpath = "//div[@class='sort']//select[@onchange='location = this.value;']")
    public WebElement selectFilterButton;

    @FindBy(xpath = "//div[@class='sort']//select[@onchange='location = this.value;']")
    public List<WebElement> filterOptions;

    @FindBy(xpath = "//label[normalize-space()='Stokta Olanlar']")
    public WebElement inStockButton;

    @FindBy(xpath = "//a[normalize-space()='2']")
    public WebElement secondPage;

    //Burada, web sitesinde ürüne tıklayabileceğim image ve span'ler tanımlanmış fakat xpath (//div[@class='pr-img-link'] [3]) şeklinde tanımlandığında 3. öğeyi bulmuyor, hepsi [1] şeklinde tanımlanmış.
    @FindBy(xpath = "//img[@alt='Kürk Mantolu Madonna / 14 Punto Serisi']")
    public WebElement selectThirdBook;

    @FindBy(xpath = "(//div[@class='price__item'])[1]")
    public WebElement productPrice;

    @FindBy(xpath = "//a[@id='button-cart']")
    public WebElement addToCartButton;

    @FindBy(xpath = "//span[@id='cart-items']")
    public WebElement cartItems;

    @FindBy(xpath = "//h4[normalize-space()='Sepetim']")
    public WebElement showCartButton;

    @FindBy(id = "js-cart")
    public WebElement goToCartButton;

    @FindBy(xpath = "//input[@name='quantity']")
    public WebElement quantityField;

    @FindBy(xpath = "//span[@class='price-new']")
    public WebElement unitPrice;

    @FindBy(xpath = "//td[contains(text(),'72,00')]")
    public WebElement totalPrice;

    @FindBy(xpath = "//i[@class='fa fa-times red-icon']")
    public WebElement clearCartButton;

}

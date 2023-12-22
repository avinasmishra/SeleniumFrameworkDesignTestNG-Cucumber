package Utility;

import PageObjectModel.CartPage;
import PageObjectModel.OrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utilities {
    private WebDriver lDriver;
    public Utilities(WebDriver rDriver) {
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }

    public void elementToAppears(By locator)
    {
        WebDriverWait wait = new WebDriverWait(lDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void elementToDisappears(WebElement element) throws InterruptedException {
        Thread.sleep(2000);
//        WebDriverWait wait = new WebDriverWait(lDriver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.invisibilityOf(element));
    }
    @FindBy(css = "[routerlink*='cart']")
    private WebElement cart;
    public CartPage goToCart()
    {
        cart.click();
        return new CartPage(lDriver);
    }

    @FindBy(css = "button[routerlink*='myorders']")
    private WebElement order;
    public OrderPage goToOrders()
    {
        order.click();
        OrderPage orderPage = new OrderPage(lDriver);
        return orderPage;
    }
}

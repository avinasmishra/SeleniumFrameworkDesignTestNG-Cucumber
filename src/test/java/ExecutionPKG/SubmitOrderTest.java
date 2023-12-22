package ExecutionPKG;

import TestComponents.BaseTest;
import PageObjectModel.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class SubmitOrderTest extends BaseTest {
    public String productName = "I PHONE";

    @Test(groups = {"Regression"})
    public void submitOrder() throws InterruptedException, IOException {
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication("ram1@gmail.com", "Ram@gmail1");

        ProductPage productPage = new ProductPage(driver);
        productPage.getProductAndAddToCart(productName);

        CartPage cartPage = productPage.goToCart();
        boolean expected = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(expected);

        CheckOutPage checkOutPage = cartPage.goToCheckOut();
        checkOutPage.selectCountry("India");

        ConfirmationPage confirmationPage = checkOutPage.goToConfimation();
        String actualResult = confirmationPage.getActualResult();
        Assert.assertTrue(actualResult.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void verifyOrderDetails() throws IOException {
        String productName = "I PHONE";
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication("ram1@gmail.com", "Ram@gmail1");
        OrderPage orderPage = loginPage.goToOrders();
        boolean order = orderPage.verifyOrderDisplay(productName);
        Assert.assertTrue(order);

    }
}

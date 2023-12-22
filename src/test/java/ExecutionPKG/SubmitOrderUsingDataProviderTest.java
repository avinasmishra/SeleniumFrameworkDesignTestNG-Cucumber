package ExecutionPKG;

import TestComponents.BaseTest;
import PageObjectModel.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class SubmitOrderUsingDataProviderTest extends BaseTest {
    //public String productName = "I PHONE";

    @Test(dataProvider = "getData")
    public void submitOrder(String email, String password, String productName) throws InterruptedException, IOException {
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication(email, password);

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

    @DataProvider
    public Object[][] getData()
    {
        return new Object[][]
                {
                    {"ram1@gmail.com","Ram@gmail1","I PHONE"},
                    {"sam11@gmail.com","Sam@gmail1","I PHONE"}
                };
    }
}

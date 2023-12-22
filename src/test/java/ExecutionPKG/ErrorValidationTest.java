package ExecutionPKG;

import TestComponents.BaseTest;
import PageObjectModel.CartPage;
import PageObjectModel.LoginPage;
import PageObjectModel.ProductPage;
import TestComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationTest extends BaseTest {

    @Test(groups = {"Regression"}, retryAnalyzer = Retry.class)
    public void invalidLogin() throws IOException {
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication("ram1@gmail.com", "Ram@gmail11");
        System.out.println(loginPage.getErrorMessage());
        Assert.assertEquals("Incorrect email or password.",loginPage.getErrorMessage());
    }

    @Test
    public void productNameValidation() throws IOException, InterruptedException {
        String productName = "I PHONE";
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication("ram1@gmail.com", "Ram@gmail1");

        ProductPage productPage = new ProductPage(driver);
        productPage.getProductAndAddToCart(productName);

        CartPage cartPage = productPage.goToCart();
        boolean expected = cartPage.verifyProductDisplay("I PHONE1");
        Assert.assertTrue(expected);
    }

}

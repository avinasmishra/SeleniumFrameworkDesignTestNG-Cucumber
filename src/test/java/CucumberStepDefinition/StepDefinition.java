package CucumberStepDefinition;

import PageObjectModel.*;
import TestComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;

public class StepDefinition extends BaseTest {
    public LoginPage loginPage;
    public ProductPage productPage;
    public CartPage cartPage;
    public CheckOutPage checkOutPage;
    public ConfirmationPage confirmationPage;

    @Given("^I landed into application$")
    public void I_landed_into_application() throws IOException {
        loginPage = launchApplication();
    }
    @Given("^enter valid username (.+) and password (.+)$")
    public void enter_valid_username_and_password(String email, String password)
    {
        loginPage.loginApplication(email, password);
    }
    @When("^I add product (.+) to cart$")
    public void I_add_product_to_cart(String productName) throws InterruptedException {
        productPage = new ProductPage(driver);
        productPage.getProductAndAddToCart(productName);
    }
    @When("^I checkout product (.+) and submit order$")
    public void I_checkout_product_and_submit_order(String productName)
    {
        cartPage = productPage.goToCart();
        boolean expected = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(expected);

        checkOutPage = cartPage.goToCheckOut();
        checkOutPage.selectCountry("India");

        confirmationPage = checkOutPage.goToConfimation();

    }
    @Then("verify the message {string} display")
    public void verify_the_message_display(String expectedResult)
    {
        String actualResult = confirmationPage.getActualResult();
        Assert.assertTrue(actualResult.equalsIgnoreCase(expectedResult));
    }

    @Then("verify the error message {string} display")
    public void verify_the_error_message_display(String expectedResult)
    {
        String actualResult = loginPage.getErrorMessage();
        Assert.assertEquals(actualResult,expectedResult);
    }


}

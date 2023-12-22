package ExecutionPKG;

import TestComponents.BaseTest;
import PageObjectModel.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubmitOrderUsingHashMapTest extends BaseTest {
    //public String productName = "I PHONE";
// NOTE: Its enhanced version of data provider- if we have 10-20 parameter we use Hashmap

    @Test(dataProvider = "getData")
    public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException {
        LoginPage loginPage = launchApplication();
        loginPage.loginApplication(input.get("email"), input.get("password"));

        ProductPage productPage = new ProductPage(driver);
        productPage.getProductAndAddToCart(input.get("productName"));

        CartPage cartPage = productPage.goToCart();
        boolean expected = cartPage.verifyProductDisplay(input.get("productName"));
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
        Map<String,String> map = new HashMap<String,String>();
        map.put("email", "ram1@gmail.com");
        map.put("password", "Ram@gmail1");
        map.put("productName", "I PHONE");

        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("email", "sam11@gmail.com");
        map1.put("password", "Sam@gmail1");
        map1.put("productName", "I PHONE");


        return new Object[][]
                {
                    {map},{map1}
                };
    }


//    @DataProvider
//    public Object[][] getData()
//    {
//        return new Object[][]
//                {
//                        {"ram1@gmail.com","Ram@gmail1","I PHONE"},
//                        {"sam11@gmail.com","Sam@gmail1","I PHONE"}
//                };
//    }
}

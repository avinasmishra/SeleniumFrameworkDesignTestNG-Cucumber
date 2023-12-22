package ExecutionPKG;

import TestComponents.BaseTest;
import JsonData.DataReader;
import PageObjectModel.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderUsingJsonFileTest extends BaseTest {
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
    public Object[][] getData() throws IOException {
        DataReader dataReader = new DataReader();
        List<HashMap<String, String>> data = dataReader.getJsonDataToHashMap(System.getProperty("user.dir")+"//src//test//java//JsonData//PurchaseOrder.json");

        return new Object[][]{ {data.get(0)},{data.get(1)} };
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

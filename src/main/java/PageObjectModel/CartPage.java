package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends Utilities {
    private WebDriver lDriver;

    public CartPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }
    @FindBy(xpath = "//div[@class='cartSection']/h3")
    private List<WebElement> cartProducts;

    public boolean verifyProductDisplay(String productName) {
        boolean value = false;
        for(int i=0;i<cartProducts.size();i++)
        {
            if(cartProducts.get(i).getText().contains(productName))
            {
                value= true;
            }
        }
        return value;
    }
    @FindBy(xpath = "//button[text()='Checkout']")
    private WebElement checkOut;

    public CheckOutPage goToCheckOut()
    {
        checkOut.click();
        return new CheckOutPage(lDriver);
    }

}

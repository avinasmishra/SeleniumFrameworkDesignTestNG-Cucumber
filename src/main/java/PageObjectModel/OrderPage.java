package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrderPage extends Utilities {
    private WebDriver lDriver;
    public OrderPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }
    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[2]")
    private List<WebElement> orders;

    public boolean verifyOrderDisplay(String productName)
    {
        boolean value = false;
        for(WebElement order:orders)
        {
            System.out.println(order.getText());
            if(order.getText().equalsIgnoreCase(productName))
            {
                value = true;
                break;
            }
        }
        return value;
    }

}

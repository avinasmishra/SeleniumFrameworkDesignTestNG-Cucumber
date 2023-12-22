package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckOutPage extends Utilities {
    private WebDriver lDriver;
    public CheckOutPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }

    @FindBy(css = "input[placeholder='Select Country']")
    private WebElement country;
    @FindBy(css = "section.ta-results button")
    private List<WebElement> countries;

    public void selectCountry(String countryName)
    {
        country.sendKeys(countryName);
        for(WebElement country1:countries)
        {
            if(country1.getText().equalsIgnoreCase(countryName)) {
                country1.click();
                break;
            }
        }
    }
    @FindBy(css = ".action__submit")
    private WebElement placeOrder;
    public ConfirmationPage goToConfimation()
    {
        placeOrder.click();
        return new ConfirmationPage(lDriver);
    }

}

package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage extends Utilities {
    private WebDriver lDriver;

    public ConfirmationPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }

    @FindBy(xpath = "//td[@align='center']/h1")
    private WebElement message;

    public String getActualResult()
    {
        return message.getText();
    }

}

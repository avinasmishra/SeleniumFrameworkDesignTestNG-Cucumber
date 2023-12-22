package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Utilities {
    private WebDriver lDriver;
    public LoginPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver,this);
    }

    //PageFactory having @FindBy locator
    @FindBy(id = "userEmail")
    private WebElement userEmail;
    @FindBy(id="userPassword")
    private WebElement password;
    @FindBy(css = "#login")
    private WebElement loginBtn;

    public void loginApplication(String email, String pwd)
    {
        userEmail.sendKeys(email);
        password.sendKeys(pwd);
        loginBtn.click();
    }
    public void goToApplication()
    {
        lDriver.get("https://rahulshettyacademy.com/client");
    }

    @FindBy(css = "[class*='flyInOut']")
    private WebElement errorMsg;
    public String getErrorMessage()
    {
        return errorMsg.getText();
    }

}

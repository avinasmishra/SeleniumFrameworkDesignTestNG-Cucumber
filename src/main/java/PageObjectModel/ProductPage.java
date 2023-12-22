package PageObjectModel;

import Utility.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends Utilities {
    private WebDriver lDriver;
    public String product;
    public ProductPage(WebDriver rDriver)
    {
        super(rDriver);
        this.lDriver=rDriver;
        PageFactory.initElements(rDriver,this);
    }
    @FindBy(css = ".col-lg-4")
    private List<WebElement> products;
    @FindBy(xpath = "//div[@class='card-body']/button[2]")
    private List<WebElement> clickToCart;
    @FindBy(css = ".ng-animating")
    private WebElement disappersEle;

    private By productAppears = By.cssSelector(".col-lg-4");
    private By prodName = By.cssSelector("b");
    private By ele_Visible = By.id("toast-container");

    public List<WebElement> getProductList()
    {
        elementToAppears(productAppears);
        return products;
    }

    public void getProductAndAddToCart(String productName) throws InterruptedException {
        for(int i=0;i<getProductList().size();i++)
        {
            product = getProductList().get(i).findElement(prodName).getText();
            if(product.equalsIgnoreCase(productName))
            {
                clickToCart.get(i).click();
                elementToAppears(ele_Visible);
                elementToDisappears(disappersEle);
                break;
            }
        }

    }

}

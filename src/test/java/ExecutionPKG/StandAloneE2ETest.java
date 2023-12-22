package ExecutionPKG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneE2ETest {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\IdeaProjects\\Software\\chromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.id("userEmail")).sendKeys("ram1@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Ram@gmail1");
        driver.findElement(By.cssSelector("#login")).click();

        String name = "ADIDAS ORIGINAL";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-lg-4")));
        List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));

        WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(name)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

//        for(int i=0;i<products.size();i++)
//        {
//            String product = products.get(i).findElement(By.cssSelector("b")).getText();
//            System.out.println("Product: "+product);
//            if(product.equalsIgnoreCase(name))
//            {
//               driver.findElements(By.xpath("//div[@class='card-body']/button[2]")).get(i).click();
//               break;
//            }
//        }

       // Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> list = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
        for(WebElement as:list)
        {
            if(as.getText().contains(name))
            {
                Assert.assertTrue(true);
            }
        }

        driver.findElement(By.xpath("//button[text()='Checkout']")).click();

        driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("Ind");
        List<WebElement> selectCountry =driver.findElements(By.cssSelector("section.ta-results button"));
        for(WebElement country:selectCountry)
        {
            if(country.getText().equalsIgnoreCase("India"))
            {
                country.click();
                break;
            }
        }
        driver.findElement(By.cssSelector(".action__submit")).click();
        String actualResult = driver.findElement(By.xpath("//td[@align='center']/h1")).getText();
        Assert.assertTrue(actualResult.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
}

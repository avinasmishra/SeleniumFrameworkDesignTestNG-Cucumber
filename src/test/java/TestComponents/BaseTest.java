package TestComponents;

import PageObjectModel.LoginPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public WebDriver initializeDriver() throws IOException {
        //property file
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Utility\\globalProperty.properties");
        properties.load(fis);

        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser");

        if(browserName.contains("chrome"))
        {
            ChromeOptions options = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\IdeaProjects\\Software\\chromeDriver\\chromedriver.exe");
            if(browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));
        }
        else if (browserName.equalsIgnoreCase("edge"))
        {
            System.setProperty("webdriver.edge.driver", "C:\\Users\\Avinash\\IdeaProjects\\Software\\edgeDriver\\msedgedriver.exe");
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public LoginPage launchApplication() throws IOException {
        driver = initializeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToApplication();
        return loginPage;
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        driver.close();
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot src = (TakesScreenshot)driver;
        File source = src.getScreenshotAs(OutputType.FILE);
        File destination = new File(System.getProperty("user.dir")+"//reports"+testCaseName+".png");
        FileUtils.copyFile(source,destination);
        return System.getProperty("user.dir")+"//reports"+testCaseName+".png";
    }
}

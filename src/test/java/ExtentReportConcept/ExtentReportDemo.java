package ExtentReportConcept;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Date;
import java.time.LocalDate;

public class ExtentReportDemo {
    public ExtentReports reports;

    @BeforeTest
    public void config()
    {
        // create object of these 2 class - ExtentReport  //ExtentSparkReporter

        String filePath = System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("Web Automation Test");
        reporter.config().setDocumentTitle("Results");

        reports = new ExtentReports();
        reports.attachReporter(reporter);
        reports.setSystemInfo("TesterName","Avinash");
        reports.setSystemInfo("Designation", "Quality Assurance");

    }

    @Test
    public void initialDemo()
    {
        reports.createTest("Initial Demo");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\IdeaProjects\\Software\\chromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/");
        System.out.println(driver.getTitle());
        driver.close();
        reports.flush();
    }

}

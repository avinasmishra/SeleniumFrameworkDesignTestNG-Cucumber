package TestComponents;

import Utility.ExtentReportNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener
{
    ExtentTest test;
    ExtentReports extentReports = ExtentReportNG.getReport();
    ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();


    @Override
    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getMethod().getMethodName());
        threadLocal.set(test);
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        threadLocal.get().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
       threadLocal.get().fail(result.getThrowable());
       String filePath = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
           e.printStackTrace();
        }
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        threadLocal.get() .addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

}

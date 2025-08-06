import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.*;

public class Practice {
    public static void main(String[] args) {
       System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //        ChromeDriver driver = new ChromeDriver();
        //https://automationexercise.com/products#google_vignette
        driver.get("https://automationexercise.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.xpath("//*[contains(text(),' Products')]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='brands-name']/ul/li"));
        int brand = list.size();
        for(int i=0;i<brand;i++)
        {
            String product = list.get(i).getText();
            String colorValue = list.get(i).getCssValue("color");
            System.out.println("COLOR IS: "+colorValue);
            System.out.println("product:"+product);
           if(product.contains("POLO"))
           {
               String prod[] = product.split("\n");
               System.out.println("count is::"+prod[0]);
               System.out.println("productName is::"+prod[1]);
               System.out.println("--------------------------");
           }
        }

      //  driver.close();

        //Another Program IMP

        driver.get("https://fast.com/");
        driver.manage().window().maximize();

        Set<String> capturedValues = new HashSet<>();

        try {
            // Monitor the changing speed value for 30 seconds
            long startTime = System.currentTimeMillis();
            long duration = 30000; // 30 seconds

            while (System.currentTimeMillis() - startTime < duration) {
                WebElement speedElement = driver.findElement(By.id("speed-value"));
                String speedText = speedElement.getText().trim();

                if (!speedText.isEmpty() && !capturedValues.contains(speedText)) {
                    capturedValues.add(speedText);
                    System.out.println("Captured speed: " + speedText);
                }
                Thread.sleep(1000); // poll every 1 second
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        System.out.println("Max speed : " + Collections.max(capturedValues));

        System.out.println("\nAll captured speeds:");
        for (String speed : capturedValues) {
            System.out.println(speed);
        }



    }
}

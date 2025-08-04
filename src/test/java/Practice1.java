import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.*;

public class Practice1 {
    public static void main(String[] args) throws InterruptedException {
//       System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\Downloads\\chromedriver-win64\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://google.com");
//        driver.manage().window().maximize();

        String[] input = {"aflows", "flow", "flo"};
        //output : flo

        String prefix = input[0];

        for(int i=1;i<input.length;i++)
        {
            while (!input[i].startsWith(prefix))
            {
                prefix = prefix.substring(0,prefix.length()-1);
if(prefix.isEmpty())
{
    System.out.println("result: 0" );
}
            }
        }
        System.out.println("result" + prefix);

    }
}

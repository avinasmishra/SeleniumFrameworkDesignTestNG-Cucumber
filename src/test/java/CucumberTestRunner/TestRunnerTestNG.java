package CucumberTestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/java/CucumberFeature/BOOAO.feature",
        glue = "CucumberStepDefinition",
        monochrome = true,
        //tags = "@LoginError",
        plugin = {"html:target/cucumber.html"}

)
public class TestRunnerTestNG extends AbstractTestNGCucumberTests {
}

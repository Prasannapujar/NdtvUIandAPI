import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    WebDriver driver;
    @BeforeClass
    public void testSetUp()
    {
       WebDriverManager.firefoxdriver().setup();
       driver= new FirefoxDriver();
       driver.get("https://www.ndtv.com/");
    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }
}

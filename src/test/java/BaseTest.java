import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    WebDriver driver;
    String apiKey="7fe67bf08c80ded756e598d6f8fedaea";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass()
    public void SetUpUi()
    {

       WebDriverManager.firefoxdriver().setup();
       driver= new FirefoxDriver();
       driver.get("https://www.ndtv.com/");
       try
        {
            driver.switchTo().alert().accept();
            System.out.println("Accepted the alert");

        }
        catch (NoAlertPresentException e)
        {
            System.out.println("Alert is not present");
        }

    }

    @BeforeClass
    public void SetupApi()
    {

        requestSpecification= new RequestSpecBuilder().setBaseUri("http://api.openweathermap.org").setContentType(ContentType.JSON).build();
        responseSpecification= new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
    }

    @AfterClass
    public void tearDownUi()
    {
        driver.quit();
    }

    @AfterClass
    public void tearDownApi()
    {

    }
}

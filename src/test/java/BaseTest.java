import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseTest {
    WebDriver driver;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @Parameters({"url"})
    @BeforeClass()
    public void SetUpUi(String url)
    {

       WebDriverManager.firefoxdriver().setup();
       driver= new FirefoxDriver();
       driver.get(url);
    }

    @Parameters({"apiKey"})
    @BeforeClass
    public void SetupApi(String apiKey)
    {

        requestSpecification= new RequestSpecBuilder().setBaseUri("http://api.openweathermap.org").setContentType(ContentType.JSON).addQueryParam("appid",apiKey).build();
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
        requestSpecification=null;
        responseSpecification=null;
    }
}

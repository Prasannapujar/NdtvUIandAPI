import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;


public class BaseTest {
    WebDriver driver;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    private static Logger log= LogManager.getLogger(BaseTest.class);
    
    @Parameters({"url"})
    @BeforeClass()
    public void SetUpUi(String url)
    {

       WebDriverManager.firefoxdriver().setup();
       driver= new FirefoxDriver();
       driver.get(url);
       log.info(" opened the  "+url +" On browser ");
    }

    @Parameters({"apiKey"})
    @BeforeClass
    public void SetupApi(String apiKey)
    {

        requestSpecification= new RequestSpecBuilder().setBaseUri("http://api.openweathermap.org").setContentType(ContentType.JSON).addQueryParam("appid",apiKey).build();
        log.debug("apiKey is "+apiKey);
        responseSpecification= new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
        log.info("Request and Response specification object are built");
    }

    @AfterClass
    public void tearDownUi()
    {
        driver.quit();
        log.info("Driver is closed ");
    }

    @AfterClass
    public void tearDownApi()
    {
        requestSpecification=null;
        responseSpecification=null;
        log.info(" Request and Response specification object are Reset ");
    }

    @BeforeSuite
    public void setLogs()
    {

        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("./log4j2.xml");
        context.setConfigLocation(file.toURI());
    }

}

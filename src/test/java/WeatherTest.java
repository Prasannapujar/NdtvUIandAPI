import io.restassured.path.json.JsonPath;
import ndtv.HomePage;
import ndtv.WeatherPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utility.NdtvWeatherDetail;
import utility.WeatherComparator;
import utility.WeatherMatcher;
import utility.openWeatherDetail;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class WeatherTest extends BaseTest {
    NdtvWeatherDetail detailUI;
    openWeatherDetail detailApi;
    private static Logger log= LogManager.getLogger(WeatherTest.class);

    /**
     * Test method to extract the weather details for given city from NDTV weather page
     */
    @Parameters({"cityName"})
    @Test(priority = 0)
    public void weatherTestUi(String cityName) {

        HomePage hp = new HomePage(driver);
        if (hp.clickOnWeather().searchAndSelect(cityName)) // if we are able to select the city
        {
            WeatherPage wp = new WeatherPage(driver);
            detailUI = wp.getWeatherDetail(cityName);

        }
        else
        {
            Assert.fail("No Result found for "+cityName);
        }

    }

    /**
     * Test method to extract weather details for given city from open weather API
     */
    @Parameters({"cityName"})
    @Test(priority = 1)
    public void weatherTestApi(String cityName) {
        requestSpecification = given().spec(requestSpecification).log().all()
                .queryParam("q", cityName);


        String response = requestSpecification.when().get("data/2.5/weather")
                .then().spec(responseSpecification).log().all().assertThat().statusCode(200).extract().response().asString();
        log.debug("Response from API "+ response);

        JsonPath jp = new JsonPath(response);
        detailApi = new openWeatherDetail();
        detailApi.setCity(cityName);
        float tempInDegree = (float) (jp.getFloat("main.temp") - 273.15); // converting from kelvin to degree
        log.debug(" Temperature in degree from API is "+Math.round(tempInDegree));
        detailApi.setTempInDegree(Math.round(tempInDegree)); // take the round of value
        log.info(" Humidity value from API is "+jp.getInt("main.humidity"));
        detailApi.setHumidity(jp.getInt("main.humidity")); // set the humidity value
        float windSpeed = (float) (jp.getFloat("wind.speed") * 3.6); // converting meter/second to KM/h
        log.debug("Wind speed value from API is "+windSpeed);
        detailApi.setWindSpeed(windSpeed);

        StringBuilder sb = new StringBuilder();
        Boolean flag = false;
        List<?> conditions = jp.getList("weather.description"); // extracting the weather description to condition
        for (Object condition : conditions) {
            sb.append(condition.toString());
            sb.append(" And ");
        }
        sb.delete(sb.length() - 5, sb.length()); // remove the last And
        log.debug(" Condition value from API is "+sb.toString());
        detailApi.setCondition(sb.toString());
    }

    /**
     * Test method to compare the temperature value of a city from NDTV and open weather API
     *   fails if the difference not within the provided range mentioned in the variable temperatureVariance
     */
    @Parameters({"temperatureVariance"})
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareTemperature(String temperatureVarianceParam)
    {
        int temperatureVariance=Integer.parseInt(temperatureVarianceParam);

        try
        {
            Assert.assertTrue(WeatherComparator.compareByTemperature(detailUI.getTempInDegree(),detailApi.getTempInDegree(),temperatureVariance));
        }
        catch(WeatherMatcher exception)
        {
            Assert.fail(exception.getMessage());
        }
    }

    /**
     * Test method to compare the Humidity value of a city from NDTV and open weather API
     * fails if the difference not within the provided range mentioned in the variable humidityVariance
     */
    @Parameters({"humidityVariance"})
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareHumidity(String humidityVarianceParam)
    {

        int humidityVariance=Integer.parseInt(humidityVarianceParam);
        try
        {
            Assert.assertTrue(WeatherComparator.compareByHumidity(detailUI.getHumidity(),detailApi.getHumidity(),humidityVariance));
        }
        catch(WeatherMatcher exception)
        {
            Assert.fail(exception.getMessage());
        }
    }

    /**
     * Test method to compare the WindSpeed value of a city from NDTV and open weather API
     * fails if the difference not within the provided range mentioned in the variable windSpeedVariance
     */
    @Parameters({"windSpeedVariance"})
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareWindSpeed(String windSpeedVarianceParam)
    {
        float windSpeedVariance=Float.parseFloat(windSpeedVarianceParam);
        try
        {
            Assert.assertTrue(WeatherComparator.compareByWindSpeed(detailUI.getWindSpeed(),detailApi.getWindSpeed(),windSpeedVariance));
        }
        catch(WeatherMatcher exception)
        {
            Assert.fail(exception.getMessage());
        }
    }



}

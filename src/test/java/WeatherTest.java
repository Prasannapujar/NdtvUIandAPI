import io.restassured.path.json.JsonPath;
import ndtv.HomePage;
import ndtv.WeatherPage;
import org.testng.Assert;
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
    int temperatureVariance=2;
    int humidityVariance=10;
    float windSpeedVariance=2.5f;
    private String cityName = "Navi Mumbai";

    /**
     * Test method to extract the weather details for given city from NDTV weather page
     */
    @Test()
    public void weatherTestUi() {

        HomePage hp = new HomePage(driver);
        if (hp.clickOnWeather().searchAndSelect(cityName)) // if we are able to select the city
        {
            WeatherPage wp = new WeatherPage(driver);
            detailUI = wp.getWeatherDetail(cityName);
            System.out.println(detailUI.getCondition() + " " + detailUI.getWindSpeed() + " " + detailUI.getHumidity() + " " + detailUI.getCity());
        }

    }

    /**
     * Test method to extract weather details for given city from open weather API
     */
    @Test
    public void weatherTestApi() {
        requestSpecification = given().spec(requestSpecification).log().all()
                .queryParam("q", cityName)
                .queryParam("appid", apiKey);

        String response = requestSpecification.when().get("data/2.5/weather")
                .then().spec(responseSpecification).log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath jp = new JsonPath(response);
        detailApi = new openWeatherDetail();
        detailApi.setCity(cityName);
        float tempInDegree = (float) (jp.getFloat("main.temp") - 273.15); // converting from kelvin to degree
        detailApi.setTempInDegree(Math.round(tempInDegree)); // take the round of value

        detailApi.setHumidity(jp.getInt("main.humidity")); // set the humidity value

        float windSpeed = (float) (jp.getFloat("wind.speed") * 3.6); // converting meter/second to KM/h
        detailApi.setWindSpeed(windSpeed);

        StringBuilder sb = new StringBuilder();
        Boolean flag = false;
        List<?> conditions = jp.getList("weather.description");
        for (Object condition : conditions) {
            sb.append(condition.toString());
            sb.append(" And ");
        }
        sb.delete(sb.length() - 5, sb.length()); // remove the last And
        detailApi.setCondition(sb.toString());

        System.out.println(detailApi.getCondition() + " " + detailApi.getWindSpeed() + " " + detailApi.getHumidity() + " " + detailApi.getCity());

        //
    }

    /**
     * Test method to compare the temperature value of a city from NDTV and open weather API
     *   fails if the difference not within the provided range mentioned in the variable temperatureVariance
     */
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareTemperature()
    {
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
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareHumidity()
    {
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
    @Test(dependsOnMethods = {"weatherTestUi","weatherTestApi"})
    public void compareWindSpeed()
    {
        try
        {
            Assert.assertTrue(WeatherComparator.compareByWindSpeed(detailUI.getWindSpeed(),detailApi.getWindSpeed(),7.25f));
        }
        catch(WeatherMatcher exception)
        {
            Assert.fail(exception.getMessage());
        }
    }



}

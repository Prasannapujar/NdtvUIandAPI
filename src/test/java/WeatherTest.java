import io.restassured.path.json.JsonPath;
import ndtv.HomePage;
import ndtv.WeatherPage;
import org.testng.annotations.Test;
import utility.NdtvWeatherDetail;
import utility.openWeatherDetail;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class WeatherTest extends BaseTest {
    NdtvWeatherDetail detailUI;
    openWeatherDetail detailApi;

   private String cityName="Navi Mumbai";
    @Test()
    public void weatherTestUi()
    {

        HomePage hp=new HomePage(driver);
       if(hp.clickOnWeather().searchAndSelect(cityName)) // if we are able to select the city
       {
           WeatherPage wp= new WeatherPage(driver);
          detailUI= wp.getWeatherDetail(cityName);
           System.out.println(detailUI.getCondition()+" "+detailUI.getWindSpeed()+" "+detailUI.getHumidity()+" "+detailUI.getCity());
       }

    }

    @Test
    public void weatherTestApi()
    {
       requestSpecification= given().spec(requestSpecification).log().all()
                .queryParam("q",cityName)
                .queryParam("appid",apiKey);

     String response= requestSpecification.when().get("data/2.5/weather")
                .then().spec(responseSpecification).log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
        JsonPath jp = new JsonPath(response);
        detailApi= new openWeatherDetail();
         detailApi.setCity(cityName);
        float tempInDegree= (float) (jp.getFloat("main.temp")-273.15); // converting from kelvin to degree
        detailApi.setTempInDegree(Math.round(tempInDegree)); // take the round of value

        detailApi.setHumidity(jp.getInt("main.humidity")); // set the humidity value

        float windSpeed= (float) (jp.getFloat("wind.speed")*3.6); // converting meter/second to KM/h
        detailApi.setWindSpeed(windSpeed);

        StringBuilder sb= new StringBuilder();
        Boolean flag=false;
        List<?> conditions=jp.getList("weather.description");
        for(Object condition:conditions)
        {
            sb.append(condition.toString());
            sb.append(" And ");
        }
        sb.delete(sb.length()-5,sb.length()); // remove the last And
        detailApi.setCondition(sb.toString());

        System.out.println(detailApi.getCondition()+" "+detailApi.getWindSpeed()+" "+detailApi.getHumidity()+" "+detailApi.getCity());

       //
    }
}

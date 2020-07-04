import ndtv.HomePage;
import ndtv.WeatherPage;
import org.testng.annotations.Test;
import utility.NdtvWeatherDetail;

public class WeatherTest extends BaseTest {
   NdtvWeatherDetail detailUI;
    @Test
    public void weatherTest()
    {
        String cityName="Navi Mumbai";
        HomePage hp=new HomePage(driver);
       if(hp.clickOnWeather().searchAndSelect(cityName)) // if we are able to select the city
       {
           WeatherPage wp= new WeatherPage(driver);
          detailUI= wp.getWeatherDetail(cityName);
           System.out.println(detailUI.getCondition());
       }

    }
}

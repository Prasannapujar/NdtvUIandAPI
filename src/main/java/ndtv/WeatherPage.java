package ndtv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.NdtvWeatherDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Weather page of Ndtv
 */
public class WeatherPage extends Page {

    private static final By loading= By.id("loading");
    private static final By searchField=By.id("searchBox");
    private static final By searchResult=By.xpath("//div[@class='message' and count(@style)=0]//input");
    private static final By citesSelected=By.xpath("//div[@class='cityText']");
    private static final By weatherDetailElement=By.xpath("//div[@class='leaflet-popup-content']//span//b");



    public WeatherPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Wait until loading text disappears
     * @return  weatherPage
     */
    public WeatherPage waitForWeatherPage()
    {
        WebDriverWait wait= new WebDriverWait(driver,10);
        WebElement loadingText=driver.findElement(loading);
        wait.until(ExpectedConditions.invisibilityOf(loadingText));
        return this;
    }

    /**
     * Perform search on Pin your City search box of weatherPage
     * @param cityName : text to be searched
     * @return WeatherPage
     */
    public WeatherPage search(String cityName)
    {
     waitForWeatherPage();
     WebDriverWait wait= new WebDriverWait(driver,5);
     WebElement searchBox=driver.findElement(searchField);
     wait.until(ExpectedConditions.visibilityOf(searchBox));
     searchBox.sendKeys(cityName);
     return this;

    }

    /**
     * Perform search and select the city
     * @param cityName : <br/> text to be searched
     * @return true: in case selected or already selected <br/> false : in case not selected
     */
    public Boolean searchAndSelect(String cityName)
    {
        search(cityName);
        WebDriverWait wait= new WebDriverWait(driver,5);
        List<WebElement> searchResults;

        try {
             searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResult));
        }catch (Exception e) {
            return false;
        }


             for(WebElement result:searchResults)
             {
                 String idValue=result.getAttribute("id");
                 if(cityName.equalsIgnoreCase(idValue))  // this will handle for search case like mumbai where mumbai and Navi mumbai appears in result
                 {
                       List<String> selectedCities=getSelectedCities();
                          if(!selectedCities.contains(idValue))  // if city is not already selected
                          {
                              result.click();  // click on the city and return true
                              return  true;
                          }
                          else
                          {
                              return true; // city is already selected no need to click, just return true
                          }
                 }
             }


        return false;

    }

    /**
     *
     * @return Returns the already selected cities names
     */
    public List<String> getSelectedCities()
    {
        List<String> cities= new ArrayList<String> ();
        WebDriverWait wait= new WebDriverWait(driver,5);
      List<WebElement> getCitiesSelected= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(citesSelected));
      for(WebElement city:getCitiesSelected)
      {
          cities.add(city.getText());
      }
      return cities;

    }

    /**
     * click on city from the map
     * @param cityName : name of the city
     * @return WeatherPage object
     */
    public WeatherPage clickOnCity(String cityName)
    {
      List<WebElement> cities=driver.findElements(citesSelected);
      for(WebElement city:cities)
      {
          if(city.getText().equalsIgnoreCase(cityName))
          {
              city.click();
          }
      }

        return this;
    }

    /**
     * click on city from map and gathers detail by parsing the popup displayed
     * @param cityName name of city
     * @return NdtvWeatherDetail: object which holds parses data
     */
    public NdtvWeatherDetail getWeatherDetail(String cityName)
{
    WebDriverWait wait= new WebDriverWait(driver,5);
    NdtvWeatherDetail weatherDetail= new NdtvWeatherDetail();
    weatherDetail.setCity(cityName);
    clickOnCity(cityName);
    List<WebElement> weatherInfo= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(weatherDetailElement));
    for(WebElement detail:weatherInfo)
    {
        String[] temp= detail.getText().split(":"); // ex Condition : Overcast split based on :
           switch (temp[0].trim())
           {
               case "Condition":weatherDetail.setCondition(temp[1].trim()); break;
               case "Wind":weatherDetail.setWindSpeed(Float.parseFloat(temp[1].split("KPH")[0].trim()));break;
               case "Humidity":weatherDetail.setHumidity(Integer.parseInt(temp[1].split("%")[0].trim()));break;
               case "Temp in Degrees":weatherDetail.setTempInDegree(Integer.parseInt(temp[1].trim()));break;
               case "Temp in Fahrenheit":weatherDetail.setTempInFahrenheit(Integer.parseInt(temp[1].trim()));break;
               default: break;
           }
    }

    return weatherDetail;

}
    }


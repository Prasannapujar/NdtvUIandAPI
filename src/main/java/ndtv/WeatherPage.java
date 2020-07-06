package ndtv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger log=LogManager.getLogger(WeatherPage.class);
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
        log.debug("waiting for loading ... symbol to disappear");
        wait.until(ExpectedConditions.invisibilityOf(loadingText));
        log.info("Weather page is displayed");
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
     log.debug("waiting for search box to display");
     wait.until(ExpectedConditions.visibilityOf(searchBox));
     log.debug("search box is displayed ");
     searchBox.sendKeys(cityName);
     log.info("Performed search using "+cityName);

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
            log.debug("waiting for search results to display in the drop down ");
             searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResult));
             log.debug("Search results are present");
        }catch (Exception e) {
            log.warn("Search result is not displayed");
            return false;
        }


             for(WebElement result:searchResults)
             {
                 String idValue=result.getAttribute("id");
                 if(cityName.equalsIgnoreCase(idValue))  // this will handle for search case like mumbai where mumbai and Navi mumbai appears in result
                 {
                     log.debug(" Matching city is found ");
                       List<String> selectedCities=getSelectedCities();
                          if(!selectedCities.contains(idValue))  // if city is not already selected
                          {
                              log.debug(" Matching city is not selected already ");
                              result.click();  // click on the city and return true
                              log.info("selected the matching city");
                              return  true;
                          }
                          else
                          {
                              log.info("Matching City is already selected");
                              return true; // city is already selected no need to click, just return true
                          }
                 }
             }

        log.info(" No Matching cities found in the suggestion ");
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
        log.debug("waiting for selected cities to be present in the DOM ");
      List<WebElement> getCitiesSelected= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(citesSelected));
      log.debug("selected cities are present in the DOM ");
      for(WebElement city:getCitiesSelected)
      {
          log.debug(city.getText()+" added to selected cities ");
          cities.add(city.getText());
      }
      log.info("returned the list of cities selected ");
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
              log.info("clicked on city"+city.getText());
              city.click();
          }else
          {
              log.debug(city.getText() + " is not matching with to be clicked city "+cityName);
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
    log.debug("Ndtv weather detail object set city name as "+cityName);
    clickOnCity(cityName);
    log.debug("waitting for weather pop up to be displayed");
    List<WebElement> weatherInfo= wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(weatherDetailElement));
    log.debug("weather info popup is displayed ");
    for(WebElement detail:weatherInfo)
    {
        String[] temp= detail.getText().split(":"); // ex Condition : Overcast split based on :
           log.debug("setting up in Ndtv weather details object "+temp[0].trim() + ":"+temp[1].trim());
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
     log.info("returned NdtvWeatherDetail object");
    return weatherDetail;

}
    }


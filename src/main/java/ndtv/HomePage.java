package ndtv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Ndtv Home page
 */

public class HomePage extends Page {
    private static Logger log= LogManager.getLogger(HomePage.class);

    private static final By moreMenuOptions= By.id("h_sub_menu");
    private static final By cancelAlert=By.xpath("//a[@class='notnow']");
    private static final By weather=By.linkText("WEATHER");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * cancel news alert and then
     * click on ... on Ndtv home page
     * @return Home Page
     */
    public HomePage clickOnMoreMenu()
    {
        cancelNewsAlert();
        WebDriverWait wait= new WebDriverWait(driver,10);
        log.debug(" waiting for ... menu option");
        wait.until(ExpectedConditions.elementToBeClickable(moreMenuOptions)).click();
        log.info("clicked on more menu option");
        return this;
    }

    /**
     * Click on ... and then click on weather page tab
     * @return WeatherPage
     */
    public WeatherPage clickOnWeather()
    {
        WebDriverWait wait= new WebDriverWait(driver,10);
        clickOnMoreMenu();
        log.debug(" waiting for weather button be clickable ");
        wait.until(ExpectedConditions.elementToBeClickable(weather)).click();
        log.info("clicked on weather button");
        return new WeatherPage(driver);

    }

    /**
     * wait for alert to be displayed and then click on No thanks
     * @return HomePage
     */
    public HomePage cancelNewsAlert()
    {
        WebDriverWait wait= new WebDriverWait(driver,30);
        log.debug(" waiting for news alert to be displayed ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelAlert)).click();
        log.info("clicked No thanks button of the alert ");
        return this;
    }
}

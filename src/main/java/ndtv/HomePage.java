package ndtv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Ndtv Home page
 */

public class HomePage extends Page {

    private static final By moreMenuOptions= By.id("h_sub_menu");
    private static final By weather=By.linkText("WEATHER");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * click on ... on Ndtv home page
     * @return Home Page
     */
    public HomePage clickOnMoreMenu()
    {
        WebDriverWait wait= new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(moreMenuOptions)).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(weather)).click();
        return new WeatherPage(driver);

    }
}

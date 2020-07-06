package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * stores weather information for a city from NDTV weather application
 */
public class NdtvWeatherDetail {

    private static Logger log= LogManager.getLogger(NdtvWeatherDetail.class);

    private String city;
    private String condition;
    private float windSpeed;
    private int humidity;
    private int tempInDegree;
    private int tempInFahrenheit;

    public void setCity(String city) {
        this.city = city;
        log.debug("Set name is "+city);
    }

    public void setCondition(String condition) {
        this.condition = condition;
        log.debug("Set condition as "+condition);
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
        log.debug("set wind speed as "+windSpeed);
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
        log.debug("set humidity as "+humidity);
    }

    public void setTempInDegree(int tempInDegree) {
        this.tempInDegree = tempInDegree;
        log.debug("set temperature in degree as "+ tempInDegree);
    }

    public void setTempInFahrenheit(int tempInFahrenheit) {
        this.tempInFahrenheit = tempInFahrenheit;
        log.debug("set temperature in fahrenheit as "+tempInFahrenheit);
    }



    public String getCity() {
        log.debug(" returned city "+city);
        return city;

    }

    public String getCondition() {
        log.debug(" Returned condition "+condition);
        return condition;
    }

    public float getWindSpeed() {
        log.debug("Returned wind speed "+windSpeed);
        return windSpeed;
    }

    public int getHumidity() {
        log.debug("returned humidity "+humidity);
        return humidity;
    }

    public int getTempInDegree() {
        log.debug(" returned temp in degree "+tempInDegree);
        return tempInDegree;
    }

    public int getTempInFahrenheit() {
        log.debug("Returned temp in Fahrenheit "+tempInFahrenheit);
        return tempInFahrenheit;
    }
}

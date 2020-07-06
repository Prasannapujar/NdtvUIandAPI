package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Stores the weather details for a city from opean weather API
 */

public class openWeatherDetail {
   private static Logger log= LogManager.getLogger(openWeatherDetail.class);
    private String city;
    private String condition;
    private float windSpeed;
    private int humidity;
    private int tempInDegree;

    public void setCity(String city) {
        this.city = city;
        log.debug("set city as "+city);
    }

    public void setCondition(String condition) {
        this.condition = condition;
        log.debug("Set condition as " + condition);
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
        log.debug(" Set wind speed as "+windSpeed);
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
        log.debug(" set humidity as "+humidity);
    }

    public void setTempInDegree(int tempInDegree) {
        this.tempInDegree = tempInDegree;
        log.debug(" set temperature in degree as "+tempInDegree);
    }

    public String getCity() {
    log.debug(" Returned city "+city);
        return city;
    }

    public String getCondition() {
        log.debug(" Returned condition "+condition);
        return condition;
    }

    public float getWindSpeed() {
        log.debug("Returned wind speed as "+windSpeed);
        return windSpeed;
    }

    public int getHumidity() {
        log.debug(" Returned humidity as "+humidity);
        return humidity;
    }

    public int getTempInDegree() {
        log.debug("Returned temp in degree as "+ tempInDegree);
        return tempInDegree;
    }
}

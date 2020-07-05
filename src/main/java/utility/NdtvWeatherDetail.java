package utility;

/**
 * stores weather information for a city from NDTV weather application
 */
public class NdtvWeatherDetail {

    private String city;
    private String condition;
    private float windSpeed;
    private int humidity;
    private int tempInDegree;
    private int tempInFahrenheit;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTempInDegree(int tempInDegree) {
        this.tempInDegree = tempInDegree;
    }

    public void setTempInFahrenheit(int tempInFahrenheit) {
        this.tempInFahrenheit = tempInFahrenheit;
    }



    public String getCity() {
        return city;
    }

    public String getCondition() {
        return condition;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTempInDegree() {
        return tempInDegree;
    }

    public int getTempInFahrenheit() {
        return tempInFahrenheit;
    }
}

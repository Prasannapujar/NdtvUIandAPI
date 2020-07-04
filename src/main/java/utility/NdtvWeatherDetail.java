package utility;

public class NdtvWeatherDetail {

    private String city;
    private String condition;
    private float windSpeed;
    private int humidity;
    private int tempInDgree;
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

    public void setTempInDgree(int tempInDgree) {
        this.tempInDgree = tempInDgree;
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

    public int getTempInDgree() {
        return tempInDgree;
    }

    public int getTempInFahrenheit() {
        return tempInFahrenheit;
    }
}

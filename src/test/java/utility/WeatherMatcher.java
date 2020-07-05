package utility;

/**
 * This exceptions is thrown when weather detail from UI and API doesn't match
 */
public class WeatherMatcher extends Exception {
    public WeatherMatcher(String s) {
        super(s);
    }
}

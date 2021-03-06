package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class file for comparing weather from UI and API
 */
public class WeatherComparator {
    private static Logger log= LogManager.getLogger(WeatherComparator.class);
    /**
     *
     * @param uiTemp  : temperature captured from UI
     * @param apiTemp : temperature captured from API
     * @param variance : variance allowed
     * @return true if the values are with in the range else throws the WeatherMatcher exception
     */
    public static boolean compareByTemperature(int uiTemp,int apiTemp,int variance) throws WeatherMatcher {
        int diff=getdifference(uiTemp,apiTemp);


           if(diff<=variance)
           {
               log.info("Temperature values are with in the range specified "+ variance);
               return true;

           } else
           {
               log.error(" Temperature values are not with in the range specified"+variance);
               throw  new WeatherMatcher(" for Temperature in degree variance of "+ diff + " was observed "+ " Expected was "+ variance);
           }
    }

    /**
     *
     * @param uiHumidity  : Humidity captured from UI
     * @param apiHumidity : Humidity capture from API
     * @param variance  : variance allowed
     * @return true if the values are with in the range else throws the WeatherMatcher exception
     */
    public static boolean compareByHumidity(int uiHumidity,int apiHumidity,int variance) throws WeatherMatcher {
        int diff=getdifference(uiHumidity,apiHumidity);

        if(diff<=variance)
        {
            log.info("Humidity values are with in the range specified "+ variance);
            return true;

        } else
        {
            log.error(" Humidity values are not with in the range specified"+variance);
            throw  new WeatherMatcher(" for Humidity variance of "+ diff + " was observed "+ " Expected was "+ variance);
        }
    }

    /**
     *
     * @param uiWindSpeed  : windSpeed captured from UI
     * @param apiWindSpeed : windspeed captured from api
     * @param variance   : variance allowed
     * @returnt true if the values are with in the range else throws the WeatherMatcher exception
     */
    public static boolean compareByWindSpeed(float uiWindSpeed,float apiWindSpeed,float variance) throws WeatherMatcher {
        float diff=getdifference(uiWindSpeed,apiWindSpeed);

        if(diff<=variance)
        {

            log.error(" Humidity values are not with in the range specified"+variance);
            return true;

        } else
        {
            log.error(" Humidity values are not with in the range specified"+variance);
            throw  new WeatherMatcher(" for WindSpeed variance of "+ diff + " was observed "+ " Expected was "+ variance);
        }
    }

    /**
     *  get the positive difference between two integer values
     * @param value1
     * @param value2
     * @return : positive difference <br> i.e  if value1 is 10 and value 20 then difference will be 10 <br> if value1 20 and value2 is 10 then also difference will be 10.00
     */
    private static int getdifference(int value1,int value2)
    {  int diff;
        if(value1>value2)
        {
            log.debug(value1+ " is greater than "+value2 );
            diff=value1-value2;
            log.debug("Difference value is "+ diff);
        }
        else
        {
         log.debug(value2+ " is greater than "+value1);
            diff=value2-value1;
            log.debug("Difference value is "+diff);
        }
        log.debug(" Difference value "+diff + " Returned ");
     return diff;
    }

    /**
     * get the positive difference two float values
     * @param value1
     * @param value2
     * @return  positive difference <br> i.e  if value1 is 10.00 and value 20.00 then difference will be 10.00 <br> if value1 20 and value2 is 10.00 then also difference will be 10
     */
    private static float getdifference(float value1,float value2)
    {  float diff;
        if(value1>value2)
        {
            log.debug(value1+ " is greater than "+value2 );
            diff=value1-value2;
            log.debug("Difference value is "+ diff);
        }
        else
        {
            log.debug(value2+ " is greater than "+value2);
            diff=value2-value1;
            log.debug("Difference value is "+diff);
        }
        log.debug(" Difference value "+diff + " Returned ");
        return diff;
    }
}

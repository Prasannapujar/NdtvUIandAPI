# Weather Comparator 
- This Project reads the Weather information of given city from NDTV weather section and  reads the the Weather information from the Openweather api's 
- Compares the temperature,Humidity and Windspeed from two sources and passes only when difference is with in set limits. 

## Example Workflow
1. Visit ndtv website’s weather page and search for Bangalore
2. Store weather object 1 w.r.t this Bangalore (e.g. temp value as 33 degree
celsius, humidity level as 70% etc.)
3. Get response from the weather API for Bangalore
4. Store the API response and build the weather object 2
5. Specify the variance logic - for e.g. 2 degree celsius for temperature & 10% for
humidity
6. Compare weather objects 1 and 2 along with the variance and mark tests as
pass or fail based on comparator response

### Prerequisites for running the tests on your machine 

  1. Java 8.
  2. Latest version on maven. 
  

### How do i run the tests in my machine?

```
mvn test
```
## How do i change the city name and variance values ? 

These values can be changed in testng.xml 

```
<test name="WeatherComparator" >
        <parameter name="cityName"             value="Bengaluru"/>
        <parameter name="temperatureVariance"  value="2"/>
        <parameter name="humidityVariance"     value="10"/>
        <parameter name="windSpeedVariance"   value="2.5"/>
        <classes>
            <class name="WeatherTest" />
        </classes>
 </test>
```
### Where can i find the log files ? 

```
target/Logfile.log
````



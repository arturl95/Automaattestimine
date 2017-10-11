package weatherapi;

import org.junit.Test;
import static org.junit.Assert.*;
import static weatherapi.WeatherApp.getThreeDayForecastMax;
import static weatherapi.WeatherApp.getThreeDayForecastMin;

public class WeatherApiTests {


    @Test
    public void testCurrentTemperature() {

        WeatherApp tester = new WeatherApp();
        try {
            assertEquals(293.25, tester.getCurrentTemperature(), 0.5);
        } catch (java.lang.Exception e) {
            assert false;
        }
    }

    @Test
    public void testgetThreeDayForecastMin() {

        WeatherApp tester = new WeatherApp();
        try {

            assertEquals(293.25, getThreeDayForecastMin());
        } catch (java.lang.Exception e) {
            assert false;
        }
    }

    @Test
    public void testgetThreeDayForecastMax() {

        WeatherApp tester = new WeatherApp();
        try {
            assertEquals("298.774", getThreeDayForecastMax());
        } catch (java.lang.Exception e) {
            assert false;
        }
    }
/*

    @Test
    public void testTemperatures() {
        WeatherApp tester = new WeatherApp();
        assertTrue(maxTemp>minTemp, tester.getTemperatures(Tallinn));
    }




    @Test
    public void testUpdateFrequency(){
        WeatherApp tester = new WeatherApp();
        assertTrue(update-previousUpdate==3, tester.getUpdateLog);

    }
    @Test
    public void testLocation(){
        WeatherApp tester = new WeatherApp();
        assertEquals("59.436","24.753", tester.getCoordinates(Tallinn));
    } */
}
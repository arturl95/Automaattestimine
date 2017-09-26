package weatherapi;

import org.junit.Test;
import weatherapi.TestedClass;

import static org.junit.Assert.*;

public class WeatherApiTests {

    @Test
    public void testTemperatures() {
        TestedClass tester = new TestedClass();
        assertTrue(maxTemp>minTemp, tester.getTemperatures(Tallinn));
    }
    @Test
    public void testCurrentTemperature() {
        TestedClass tester = new TestedClass();
        assertEquals(10, tester.getCurrentTemperature(Tallinn) );

    }
    @Test
    public void testUpdateFrequency(){
        TestedClass tester = new TestedClass();
        assertTrue(update-previousUpdate==3, tester.getUpdateLog);

    }
    @Test
    public void testLocation(){
        TestedClass tester = new TestedClass();
        assertEquals("59.436","24.753", tester.getCoordinates(Tallinn));
    }
}
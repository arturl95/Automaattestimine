package tests;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import weatherapi.WeatherData;
import weatherapi.WeatherForecast;
import weatherapi.WeatherIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;



public class WeatherApiMockTests {
    public static WeatherIO weatherIOSpy;
    public static WeatherData weatherDataSpy;
    public static WeatherForecast weatherForecastSpy;

    public static WeatherIO weatherIOmock;
    public static WeatherForecast weatherForecastmock;
    public static WeatherData weatherDatamock;

    public static WeatherData weatherDataObject = new WeatherData();

    public static String cityName = "Tallinn";



    @BeforeClass
    public static void setUpTesting() {

        weatherIOSpy = spy(new WeatherIO());
        weatherDataSpy = spy(new WeatherData());
        weatherForecastSpy = spy(new WeatherForecast());

        weatherIOmock = Mockito.mock(WeatherIO.class);
        weatherForecastmock = Mockito.mock(WeatherForecast.class);
        weatherDatamock = Mockito.mock(WeatherData.class);

    }


    @AfterClass
    public static void deleteFiles() throws IOException {


        if (Files.exists(Paths.get("{" + cityName + "}.txt"))) {
            Files.delete(Paths.get("{" + cityName + "}.txt"));
        }
    }


    @Test
    public void testSearchCityCall() throws Exception {
        weatherForecastSpy.searchCity(cityName);
        verify(weatherForecastSpy).getCoord(Mockito.<WeatherData>anyObject());

    }


    @Before
    public void resetSpy() {
        weatherIOSpy = spy(new WeatherIO());
        weatherDataSpy = spy(new WeatherData());
        weatherForecastSpy = spy(new WeatherForecast());

    }

    @Test
    public void testGetCoordCall() throws Exception {

        weatherDataObject.setCityName("Tallinn");
        weatherDataObject.setCityID("590447");
        weatherForecastSpy.getCoord(weatherDataObject);
        verify(weatherForecastSpy).getData(Mockito.<WeatherData>anyObject());

    }


    @Test
    public void testGetDataCall() throws Exception {

        weatherDataObject.setCityName("Tallinn");
        weatherDataObject.setCityID("590447");
        weatherForecastSpy.getData(weatherDataObject);
        verify(weatherForecastSpy).getCurrentTemperature(Mockito.<WeatherData>anyObject());
        verify(weatherForecastSpy).getThreeDayForecast(Mockito.<WeatherData>anyObject());
    }

    @Test
    public void testCurrentTemperature() {
        Random random = new Random();
        double randomNumber = 0.0 + (99.9 - 0.0) * random.nextDouble();
        when(weatherDatamock.getCurrentTemperature()).thenReturn(randomNumber);
        assertTrue(weatherDatamock.getCurrentTemperature() == randomNumber);

    }


    @Test
    public void testGetCoordCallingArgument() throws Exception {
        ArgumentCaptor<WeatherData> argument = ArgumentCaptor.forClass(WeatherData.class);

        weatherDataObject = new WeatherData();
        weatherDataObject.setCityName("Tallinn");
        weatherDataObject.setCityID("590447");

        weatherForecastSpy.getCoord(weatherDataObject);
        verify(weatherForecastSpy).getData(argument.capture());
        assertEquals(argument.getValue(), weatherDataObject);


    }


    @Test
    public void testFileCreation() throws Exception {
        ArrayList<Double> testList = new ArrayList<>();
        testList.add(1.1);

        weatherDataSpy.setCityName("TestCity");
        weatherDataSpy.setTempToday(testList);
        weatherDataSpy.setTempTomorrow(testList);
        weatherDataSpy.setTempAfterTomorrow(testList);

        weatherIOSpy.publishResults(weatherDataSpy);

        Path path = Paths.get("{TestCity}.txt");
        boolean ifFileExists = false;
        if (Files.exists(path)) {
            ifFileExists = true;
        }

        assertEquals(true,ifFileExists);
        Files.delete(Paths.get("{TestCity}.txt"));
    }
/*
    @Test
    public void testFileReader() throws Exception {

        FileWriter fileWriter = new FileWriter("input.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(cityName);
        printWriter.flush();

        String input="Y";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        weatherIOSpy.enterInput();
        System.setIn(stream);
        String fileOutput = new String(Files.readAllBytes(Paths.get("input.txt")));


        assertEquals(cityName+"\r\n",fileOutput);
        Files.delete(Paths.get("{"+cityName+"}.txt"));


    } */



}

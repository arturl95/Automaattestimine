package tests;



import org.junit.*;
import weatherapi.WeatherData;
import weatherapi.WeatherForecast;
import weatherapi.WeatherIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;



public class WeatherApiTests {
    WeatherIO tester = new WeatherIO();
    WeatherData testingObject = new WeatherData();
    private String cityName="Riga";


    private final ByteArrayOutputStream systemOutPrint = new ByteArrayOutputStream();


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(systemOutPrint));

    }


    @Test
    public void testInputRandom() {

        WeatherIO dataInput = new WeatherIO();

        String input="qfdgd";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));


        try {
            dataInput.enterInput();
            System.setIn(stream);
            assertEquals("Should the program read city input from a file? (Y/N): \r\nInput not valid\r\n", systemOutPrint.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testFileAndConsoleOutputMatching() throws Exception {
        WeatherIO test = new WeatherIO();

        FileWriter fileWriter = new FileWriter("input.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(cityName);
        printWriter.flush();

        String input="Y";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        test.enterInput();
        System.setIn(stream);

        String fileOutput = new String(Files.readAllBytes(Paths.get("{"+cityName+"}.txt")));
        String consoleFirstLine = "Should the program read city input from a file? (Y/N): \r\n";

        assertEquals(systemOutPrint.toString(),consoleFirstLine+fileOutput);
        Files.delete(Paths.get("{"+cityName+"}.txt"));


    }


    @After
    public void cleanUpStreams() {
        System.setOut(null);

    }


    @Test
    public void testTodayDate(){

        Date todayDate =  new Date();
        Date tomorrowDate = new Date(todayDate.getTime()+(1000*60*60*24));
        WeatherForecast tester = new WeatherForecast();

        assertEquals("Tomorrow",tester.determineDate(tomorrowDate));
    }

    @Test
    public void testTomorrowDate(){

        Date todayDate =  new Date();
        Date dayAfterTomorrowDate = new Date(todayDate.getTime()+(2*1000*60*60*24));
        WeatherForecast tester = new WeatherForecast();

        assertEquals("AfterTomorrow", tester.determineDate(dayAfterTomorrowDate));
    }

    @Test
    public void testErrorDate(){

        Date todayDate =  new Date();
        Date error = new Date(todayDate.getTime()+(10*1000*60*60*24));
        WeatherForecast tester = new WeatherForecast();

        assertEquals("error", tester.determineDate(error));
    }


    @Test
    public void testDayAfterTomorrowDate(){

        Date todayDate =  new Date();
        WeatherForecast tester = new WeatherForecast();

        assertEquals("Today", tester.determineDate(todayDate));
    }










    @Test
    public void testLongitude() throws Exception {
        WeatherForecast tester = new WeatherForecast();
        WeatherData testingObject;
        testingObject=tester.searchCity("Riga");
        testingObject.setCityName("Riga");



        testingObject = tester.getCoord(testingObject);
        String longitude = testingObject.getLongitude();
        assertEquals("24.11",longitude);

    }

    @Test
    public void testLatitude() throws Exception {
        WeatherForecast tester = new WeatherForecast();
        WeatherData testingObject;
        testingObject=tester.searchCity("Riga");
        testingObject.setCityName("Riga");

        testingObject = tester.getCoord(testingObject);
        String latitude = testingObject.getLatitude();
        assertEquals("56.95",latitude);

    }


    @Test
    public void testCoordinatesInFile() throws Exception {
        WeatherForecast tester = new WeatherForecast();
        WeatherData dataObject = tester.searchCity(cityName);
        String coordinateLine = Files.readAllLines(Paths.get("{"+ cityName + "}.txt")).get(1);
        String longitude = dataObject.getLongitude();
        String latitude = dataObject.getLatitude();
        String coordinates = "("+longitude+":"+latitude+")";
        assertEquals(coordinates,coordinateLine);
    }





    @Before
    public void setUpWeatherDataObject(){
        testingObject.setCityName("TestCity");
        testingObject.setCityID("123456");

        ArrayList<Double> testList = new ArrayList<>();
        testList.add(1.1);
        testList.add(9.9);
        testList.add(4.4);
        testingObject.setTempToday(testList);
        testingObject.setTempTomorrow(testList);
        testingObject.setTempAfterTomorrow(testList);
        testingObject.setCurrentTemperature(1995.0);

    }




    @Test
    public void testCheckIfFileIsCreated() throws Exception {

        tester.publishResults(testingObject);
        Path path = Paths.get("{TestCity}.txt");
        boolean ifFileExists = false;
        if (Files.exists(path)) {
            ifFileExists = true;
        }

        assertEquals(true,ifFileExists);
        Files.delete(Paths.get("{TestCity}.txt"));
    }


    @Test
    public void testIfReturnsCorrectMaximumTemp() throws Exception {

        testingObject = tester.publishResults(testingObject);
        String output = testingObject.getMaximum();

        assertEquals("9.9",output);
        Files.delete(Paths.get("{TestCity}.txt"));

    }

    @Test
    public void testIfReturnsCorrectMinimumTemp() throws Exception {

        testingObject = tester.publishResults(testingObject);
        String output = testingObject.getMinimum();

        assertEquals("1.1",output);
        Files.delete(Paths.get("{TestCity}.txt"));

    }


    @After
    public void  cleanUpWeatherDataObject(){
        testingObject=null;
        tester=null;

    }





    }


package weatherapi;



import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;



public class WeatherApiTests {
    //City name to test
    private String cityName="Riga";

    public void setUp(){
       MockitoAnnotations.initMocks(this);
    }


    @Mock
    WeatherApp app= new WeatherApp();

  //  @Rule
  // public MockitoRule mockitoRule = MockitoJUnit.rule();


    private final ByteArrayOutputStream systemOutPrint = new ByteArrayOutputStream();


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(systemOutPrint));

    }


    @Test
    public void testInputRandom() {
        //WeatherApp weatherAppMock = mock(WeatherApp.class);
        WeatherApp weatherAppMock = new WeatherApp();

        String input="q";
        InputStream stream = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));


        try {
            weatherAppMock.enterInput();
            System.setIn(stream);
            assertEquals("Should the program read city input from a file? (Y/N): \r\nInput not valid\r\n", systemOutPrint.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);

    }


    @Before
    public void SetUp() throws IOException {

        FileWriter fileWriter = new FileWriter("input.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(cityName);
        printWriter.flush();

        System.setOut(new PrintStream(systemOutPrint));



    }


    @Ignore
    public void testReadFromFile(){
        //WeatherApp weatherAppMock = mock(WeatherApp.class);
        WeatherApp weatherAppMock = new WeatherApp();



        try {
            weatherAppMock.readFromFile();
            assertEquals("City found in file: "+cityName, systemOutPrint.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @After
    public void cleanUp() {
        System.setOut(null);

    }




    @Test
    public void testTodayDate(){

        Date todayDate =  new Date();
        Date tomorrowDate = new Date(todayDate.getTime()+(1000*60*60*24));
        WeatherApp weatherApp = new WeatherApp();

        assertEquals("Tomorrow",weatherApp.determineDate(tomorrowDate));
    }

    @Test
    public void testTomorrowDate(){

        Date todayDate =  new Date();
        Date dayAfterTomorrowDate = new Date(todayDate.getTime()+(2*1000*60*60*24));
        WeatherApp weatherApp = new WeatherApp();

        assertEquals("AfterTomorrow",weatherApp.determineDate(dayAfterTomorrowDate));
    }

    @Test
    public void testErrorDate(){

        Date todayDate =  new Date();
        Date error = new Date(todayDate.getTime()+(10*1000*60*60*24));
        WeatherApp weatherApp = new WeatherApp();

        assertEquals("error",weatherApp.determineDate(error));
    }


    @Test
    public void testDayAfterTomorrowDate(){
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate =  new Date();
        WeatherApp weatherApp = new WeatherApp();

        assertEquals("Today",weatherApp.determineDate(todayDate));
    }



    @Test
    public void testReadUrl() throws Exception {

        WeatherApp weatherApp = new WeatherApp();
       String text=weatherApp.readUrl("http://dijkstra.cs.ttu.ee/~arlusm/cgi-bin/prax3/programm.py");
        assertEquals("",text);
    }


    @Test
    public void testCurrentTemperature() {

        WeatherApp tester = new WeatherApp();
        try {

        //assertEquals(293.25, tester.getCurrentTemperature(456172,Riga), 0.5);

        } catch (java.lang.Exception e) {
            assert false;
        }
    }


    }


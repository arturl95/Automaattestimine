package weatherapi;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Artur on 22.09.2017.
 */
public class WeatherApp {


    /*{
        "id": 588409,
            "name": "Tallinn",
            "country": "EE",
            "coord": {
        "lon": 24.753531,
                "lat": 59.436958
    } String url ="http://www.api.openweathermap.org/data/2.5/weather?id=588409&APPID=ae2192ccd7d3abe97a1b3c1be195af48";

  api.openweathermap.org/data/2.5/forecast?id=524901&APPID=ae2192ccd7d3abe97a1b3c1be195af48

    */

    public static void main(String[] args) throws Exception {


        System.out.println(getThreeDayForecastMin());
        System.out.println(getThreeDayForecastMax());

    }

    public static double getCurrentTemperature() throws Exception{

        String jsonData = readUrl("http://www.api.openweathermap.org/data/2.5/weather?id=588409&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern currentTemp = Pattern.compile("temp\":(.*?),");
        Matcher matcher = currentTemp.matcher(jsonData);
        if (matcher.find())
        {
            return Double.parseDouble(matcher.group(1));
        }
        return -1;
    }


    public static String getThreeDayForecastMin() throws Exception{

        String jsonData = readUrl("api.openweathermap.org/data/2.5/forecast?id=588409&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern minTemp = Pattern.compile("temp_min\":(.*?),");
        Matcher matcher = minTemp.matcher(jsonData);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find())
        {
            buffer.append(matcher.group(1) + "\n" );




        } return buffer.toString();



    }

    public static String getThreeDayForecastMax() throws Exception{

        String jsonData = readUrl("api.openweathermap.org/data/2.5/forecast?id=588409&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern minTemp = Pattern.compile("temp_max\":(.*?),");
        Matcher matcher = minTemp.matcher(jsonData);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find())
        {
            buffer.append(matcher.group(1) + "\n" );




        } return buffer.toString();



    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];

            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}

package weatherapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static weatherapi.WeatherIO.readUrl;

public class WeatherForecast {

WeatherIO object = new WeatherIO();

    public  WeatherData searchCity(String cityName) throws Exception {



        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&APPID=ae2192ccd7d3abe97a1b3c1be195af48");


        Pattern idPattern = Pattern.compile("},\"id\":(.*?),");
        Matcher matcher = idPattern.matcher(jsonData);

        if (matcher.find() ) {
            String cityID=matcher.group(1);


            WeatherData weatherObject = new WeatherData(cityID, cityName);
            getCoord(weatherObject);
            return weatherObject;

        }



        throw new Exception("City not found");
    }


    public WeatherData getCoord(WeatherData weatherObject) throws Exception {

        String cityName=weatherObject.getCityName();

        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&APPID=ae2192ccd7d3abe97a1b3c1be195af48");


        Pattern longitude = Pattern.compile("\"lon\":(.*?),");
        Pattern latitude = Pattern.compile("\"lat\":(.*?)}");
        Matcher longitudeMatcher = longitude.matcher(jsonData);
        Matcher latitudeMatcher = latitude.matcher(jsonData);
        while (longitudeMatcher.find() && latitudeMatcher.find() ) {

            String lonCoord=longitudeMatcher.group(1);
            String latCoord=latitudeMatcher.group(1);


            weatherObject.setLongitude(lonCoord);
            weatherObject.setLatitude(latCoord);



            getData(weatherObject);
            return weatherObject;

        }


        throw new Exception("Coordinates not found");
    }


    public void getData(WeatherData weatherObject) throws Exception {

        getCurrentTemperature(weatherObject);
        getThreeDayForecast(weatherObject);

    }

    public double getCurrentTemperature(WeatherData weatherObject) throws Exception {
        String cityID=weatherObject.getCityID();

        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&units=metric&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern currentTemp = Pattern.compile("temp\":(.*?),");
        Matcher matcher = currentTemp.matcher(jsonData);
        if (matcher.find()) {
            double currentTemperature = Double.parseDouble(matcher.group(1));
            weatherObject.setCurrentTemperature(currentTemperature);
            return currentTemperature;
        }
        throw new Exception();
    }



    public WeatherData getThreeDayForecast(WeatherData weatherObject) throws Exception {

        String cityID=weatherObject.getCityID();



        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/forecast?id=" + cityID + "&units=metric&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern minTemp = Pattern.compile("temp_min\":(.*?),");
        Matcher matcher = minTemp.matcher(jsonData);

        Pattern date = Pattern.compile("dt_txt\":\"(.*?) ");
        Matcher dateMatcher = date.matcher(jsonData);
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");


        ArrayList<Double> TempToday = new ArrayList<>();
        ArrayList<Double> TempTomorrow = new ArrayList<>();
        ArrayList<Double> TempAfterTomorrow = new ArrayList<>();

        while (matcher.find() && dateMatcher.find()) {
            Date formattedDate = today.parse(dateMatcher.group(1));


            if (determineDate(formattedDate).equals("Today")) {
                TempToday.add(Double.valueOf(matcher.group(1)));
            } else if (determineDate(formattedDate).equals("Tomorrow")) {
                TempTomorrow.add(Double.valueOf(matcher.group(1)));
            } else if (determineDate(formattedDate).equals("AfterTomorrow")) {
                TempAfterTomorrow.add(Double.valueOf(matcher.group(1)));
            }


        }
            Pattern maxTemp = Pattern.compile("temp_max\":(.*?),");
            matcher = maxTemp.matcher(jsonData);
            while (matcher.find() && dateMatcher.find()) {

            Date formattedDate2 = today.parse(dateMatcher.group(1));

            if (determineDate(formattedDate2).equals("Today")) {
                TempToday.add(Double.valueOf(matcher.group(2)));
            } else if (determineDate(formattedDate2).equals("Tomorrow")) {
                TempTomorrow.add(Double.valueOf(matcher.group(2)));
            } else if (determineDate(formattedDate2).equals("AfterTomorrow")) {
                TempAfterTomorrow.add(Double.valueOf(matcher.group(2)));
            }


        }





        weatherObject.setTempToday(TempToday);
        weatherObject.setTempTomorrow(TempTomorrow);
        weatherObject.setTempAfterTomorrow(TempAfterTomorrow);



        object.publishResults(weatherObject);

        return weatherObject;


    }

   public String determineDate(Date inputDate) {
        Date todayDate = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(todayDate);


        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(todayDate);
        tomorrow.add(Calendar.DATE, 1);


        Calendar afterTomorrow = Calendar.getInstance();
        afterTomorrow.setTime(todayDate);
        afterTomorrow.add(Calendar.DATE, 2);

        Calendar keyDate = Calendar.getInstance();
        keyDate.setTime(inputDate);


        if (today.get(Calendar.DAY_OF_YEAR) == keyDate.get(Calendar.DAY_OF_YEAR)) {

            return "Today";

        } else if (tomorrow.get(Calendar.DAY_OF_YEAR) == keyDate.get(Calendar.DAY_OF_YEAR)) {

            return "Tomorrow";

        } else if (afterTomorrow.get(Calendar.DAY_OF_YEAR) == keyDate.get(Calendar.DAY_OF_YEAR)) {
            return "AfterTomorrow";

        }
        return "error";
    }



}
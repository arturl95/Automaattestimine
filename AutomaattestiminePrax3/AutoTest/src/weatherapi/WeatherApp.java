package weatherapi;


import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Artur on 22.09.2017.
 */
public class WeatherApp {


    public static void main(String[] args) throws Exception {

        WeatherApp helper = new WeatherApp();

        helper.enterInput();


    }

      void enterInput() throws Exception {
        System.out.println("Should the program read city input from a file? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next().toUpperCase();
        if (input.equals("Y")) {
            readFromFile();

        } else if (input.equals("N")) {
            System.out.println("Enter the city name: ");
            String cityName = scanner.next();
            searchCity(cityName);
        } else {
            System.out.println("Input not valid");


        }
    }

     void readFromFile() throws  Exception{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String line = bufferedReader.readLine();
            System.out.println("City found in file: " + line);
            searchCity(line);

    }


     void searchCity(String cityName) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("city.list.json"));
        Scanner scanner = new Scanner(bufferedReader);
        String cityID = null;
        while (scanner.hasNextLine()) {
            String city = scanner.nextLine();
            if (city.contains(cityName)) {
                System.out.println("city ID is " + cityID);
                Pattern cityPattern = Pattern.compile("id\": (.*?),");
                Matcher matcher = cityPattern.matcher(cityID);
                matcher.find();
                String parsedID = matcher.group(1);
                System.out.println("matcher found:" +parsedID);
                getData(parsedID, cityName);
                break;
            }
            cityID = city;


        }
    }



     void getData(String cityID, String cityName) throws Exception {

        getCurrentTemperature(cityID, cityName);
        getThreeDayForecastMax(cityID,cityName);
        getThreeDayForecastMin(cityID,cityName);




    }

      double getCurrentTemperature(String cityID, String cityName) throws Exception{

        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/weather?id="+cityID+"&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern currentTemp = Pattern.compile("temp\":(.*?),");
        Matcher matcher = currentTemp.matcher(jsonData);
        if (matcher.find())
        {
            //return Double.parseDouble(matcher.group(1));
            String print = "Current temperature in " +cityName + " is " + matcher.group(1) + "degrees";
            System.out.println(print);
            FileWriter fileWriter = new FileWriter("output.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(print);
            printWriter.flush();

        }


        return -1;
    }


      String getThreeDayForecastMin(String cityID, String cityName) throws Exception{

        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/forecast?id="+cityID+"&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern minTemp = Pattern.compile("temp_min\":(.*?),");
        Matcher matcher = minTemp.matcher(jsonData);

        Pattern date = Pattern.compile("dt_txt\":\"(.*?) ");
        Matcher dateMatcher = date.matcher(jsonData);
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");




        ArrayList<Double> minTempToday = new ArrayList<>();
        ArrayList<Double> minTempTomorrow = new ArrayList<>();
        ArrayList<Double> minTempAfterTomorrow = new ArrayList<>();
        //siin tuleb juba päeva järgi ära sortida
        while (matcher.find() && dateMatcher.find())
        {
            Date formattedDate = today.parse(dateMatcher.group(1));

            if (determineDate(formattedDate).equals("Today")){
                minTempToday.add(Double.valueOf(matcher.group(1)));
            }
            else if (determineDate(formattedDate).equals("Tomorrow")){
                minTempTomorrow.add(Double.valueOf(matcher.group(1)));
            }
            else if (determineDate(formattedDate).equals("AfterTomorrow")){
                minTempAfterTomorrow.add(Double.valueOf(matcher.group(1)));
            }



        }


        determineExtreme(minTempToday,-1,"today",cityName);
        determineExtreme(minTempTomorrow,-1,"tomorrow",cityName);
        determineExtreme(minTempAfterTomorrow,-1,"day after tomorrow",cityName);
        return "";


    }

     String getThreeDayForecastMax(String cityID, String cityName) throws Exception{

        String jsonData = readUrl("http://api.openweathermap.org/data/2.5/forecast?id="+cityID+"&APPID=ae2192ccd7d3abe97a1b3c1be195af48");
        Pattern maxTemp = Pattern.compile("temp_max\":(.*?),");
        Matcher matcher = maxTemp.matcher(jsonData);

        Pattern date = Pattern.compile("dt_txt\":\"(.*?) ");
        Matcher dateMatcher = date.matcher(jsonData);
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Double> maxTempToday = new ArrayList<>();
        ArrayList<Double> maxTempTomorrow = new ArrayList<>();
        ArrayList<Double> maxTempAfterTomorrow = new ArrayList<>();


        while (matcher.find() && dateMatcher.find())
        {
            Date formattedDate = today.parse(dateMatcher.group(1));

            if (determineDate(formattedDate).equals("Today")){
                maxTempToday.add(Double.valueOf(matcher.group(1)));
            }
            else if (determineDate(formattedDate).equals("Tomorrow")){
                maxTempTomorrow.add(Double.valueOf(matcher.group(1)));
            }
            else if (determineDate(formattedDate).equals("AfterTomorrow")){
                maxTempAfterTomorrow.add(Double.valueOf(matcher.group(1)));
            }


        }


        determineExtreme(maxTempToday,1,"today",cityName);
        determineExtreme(maxTempTomorrow,1,"tomorrow",cityName);
        determineExtreme(maxTempAfterTomorrow,1,"day after tomorrow",cityName);
        return "";


    }




     String determineDate(Date inputDate){
        Date todayDate = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(todayDate);


        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(todayDate);
        tomorrow.add(Calendar.DATE, 1);


        Calendar afterTomorrow = Calendar.getInstance();
        afterTomorrow.setTime(todayDate);
        afterTomorrow.add(Calendar.DATE,2);

        Calendar keyDate = Calendar.getInstance();
        keyDate.setTime(inputDate);


        if (today.get(Calendar.DAY_OF_YEAR)== keyDate.get(Calendar.DAY_OF_YEAR)){

            return "Today";

        }else if (tomorrow.get(Calendar.DAY_OF_YEAR)== keyDate.get(Calendar.DAY_OF_YEAR)){

            return "Tomorrow";

        }else if (afterTomorrow.get(Calendar.DAY_OF_YEAR)== keyDate.get(Calendar.DAY_OF_YEAR)){
            return "AfterTomorrow";

        }
        return "error";
    }



      static void determineExtreme(ArrayList<Double> DaysTemperature, int category, String currentDay, String cityName) throws Exception{

        if (category == -1) {



            String print = ("Minimum temperature in " + cityName + " " + currentDay + " is " + Collections.min(DaysTemperature) + " degrees");
            System.out.println(print);
            FileWriter fileWriter = new FileWriter("output.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(print);
            printWriter.flush();


        }



        else if (category == 1) {
            String print = ("Maximum temperature in " + cityName +" " + currentDay + " is " + Collections.min(DaysTemperature) + " degrees");
            System.out.println(print);
            FileWriter fileWriter = new FileWriter("output.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(print);
            printWriter.flush();

        }




    }


       static String readUrl(String urlString) throws Exception {
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

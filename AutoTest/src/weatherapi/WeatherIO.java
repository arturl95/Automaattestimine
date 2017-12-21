package weatherapi;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class WeatherIO {





        public void enterInput() throws Exception {
            System.out.println("Should the program read city input from a file? (Y/N): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next().toUpperCase();
            if (input.equals("Y")||input.equals("YES") ) {
                readFromFile();

            } else if (input.equals("N")||input.equals("NO") ) {
                String newLineAbsorber = scanner.nextLine();
                System.out.println("Enter the city name: ");
                String cityName = scanner.nextLine();
                WeatherForecast forecastObject = new WeatherForecast();
                forecastObject.searchCity(cityName);

            } else {
                System.out.println("Input not valid");


            }
        }

      public void readFromFile() throws Exception {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String line;


            while ((line = bufferedReader.readLine()) != null) {
                WeatherForecast forecastObject = new WeatherForecast();
                forecastObject.searchCity(line);

            }

        }





       public static String readUrl(String urlString) throws Exception {
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

    public WeatherData publishResults(WeatherData weatherObject) throws IOException {

        String cityName = weatherObject.getCityName();
        String longitude = weatherObject.getLongitude();
        String latitude = weatherObject.getLatitude();
        String coordinates = "("+longitude+":"+latitude+")" ;


        ArrayList<Double> today = weatherObject.getTempToday();
        ArrayList<Double> tomorrow = weatherObject.getTempTomorrow();
        ArrayList<Double> afterTomorrow = weatherObject.getTempAfterTomorrow();

        FileWriter fileWriter = new FileWriter("{"+cityName + "}.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        String minToday = ""+Collections.min(today);
        String maxToday = ""+ Collections.max(today);
        String minTomorrow = ""+ Collections.min(tomorrow);
        String maxTomorrow = ""+ Collections.max(tomorrow);
        String minAfterTomorrow = ""+ Collections.min(afterTomorrow);
        String maxAfterTomorrow = ""+  Collections.max(afterTomorrow);


        String printMinimum = ("Minimum temperature today is " + Collections.min(today) + " degrees");

        String printMaximum = ("Maximum temperature today is " + Collections.max(today) + " degrees");

        String printMinimumTomorrow = ("Minimum temperature tomorrow is " + Collections.min(tomorrow) + " degrees");
        String printMaximumTomorrow = ("Maximum temperature tomorrow is " + Collections.max(tomorrow) + " degrees");

        String printMinimumAfterTomorrow = ("Minimum temperature day after tomorrow is " + Collections.min(afterTomorrow) + " degrees");
        String printMaximumAfterTomorrow = ("Maximum temperature day after tomorrow is " + Collections.max(afterTomorrow) + " degrees");

        String currentTemp = "Current temperature is "+weatherObject.getCurrentTemperature() + " degrees";


        weatherObject.setTemperatures(minToday,maxToday,minTomorrow,maxTomorrow,minAfterTomorrow,maxAfterTomorrow);

        System.out.println(cityName);
        System.out.println(coordinates);
        System.out.println(printMinimum);
        System.out.println(printMaximum);
        System.out.println(printMinimumTomorrow);
        System.out.println(printMaximumTomorrow);
        System.out.println(printMinimumAfterTomorrow);
        System.out.println(printMaximumAfterTomorrow);
        System.out.println(currentTemp);



        printWriter.println(cityName);
        printWriter.println(coordinates);

        printWriter.println(printMinimum);
        printWriter.println(printMaximum);

        printWriter.println(printMinimumTomorrow);
        printWriter.println(printMaximumTomorrow);

        printWriter.println(printMinimumAfterTomorrow);
        printWriter.println(printMaximumAfterTomorrow);
        printWriter.println(currentTemp);



        printWriter.flush();
        fileWriter.close();



        return weatherObject;

    }



}

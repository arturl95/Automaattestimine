package weatherapi;

import java.util.ArrayList;


public class WeatherData {

    String cityID;
    String cityName;
    String longitude;
    String latitude;
    Double currentTemperature;
    String Minimum;
    String Maximum;
    String MinimumTomorrow;
    String MaximumTomorrow;
    String MinimumAfterTomorrow;
    String MaximumAfterTomorrow;


    ArrayList<Double> TempToday = new ArrayList<>();
    ArrayList<Double> TempTomorrow = new ArrayList<>();
    ArrayList<Double> TempAfterTomorrow = new ArrayList<>();

    public WeatherData() {
    }

    public WeatherData(String cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }





    public String getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Double> getTempToday() {
        return TempToday;
    }

    public void setTempToday(ArrayList<Double> tempToday) {
        TempToday = tempToday;
    }

    public ArrayList<Double> getTempTomorrow() {
        return TempTomorrow;
    }

    public void setTempTomorrow(ArrayList<Double> tempTomorrow) {
        TempTomorrow = tempTomorrow;
    }

    public ArrayList<Double> getTempAfterTomorrow() {
        return TempAfterTomorrow;
    }

    public void setTempAfterTomorrow(ArrayList<Double> tempAfterTomorrow) {
        TempAfterTomorrow = tempAfterTomorrow;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getMinimum() {
        return Minimum;
    }

    public String getMaximum() {
        return Maximum;
    }

    public String getMinimumTomorrow() {
        return MinimumTomorrow;
    }

    public String getMaximumTomorrow() {
        return MaximumTomorrow;
    }

    public String getMinimumAfterTomorrow() {
        return MinimumAfterTomorrow;
    }

    public String getMaximumAfterTomorrow() {
        return MaximumAfterTomorrow;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public void setTemperatures(String minimum, String maximum, String minimumTomorrow, String maximumTomorrow, String minimumAfterTomorrow, String maximumAfterTomorrow) {
        Minimum = minimum;
        Maximum = maximum;
        MinimumTomorrow = minimumTomorrow;
        MaximumTomorrow = maximumTomorrow;
        MinimumAfterTomorrow = minimumAfterTomorrow;
        MaximumAfterTomorrow = maximumAfterTomorrow;
    }


}


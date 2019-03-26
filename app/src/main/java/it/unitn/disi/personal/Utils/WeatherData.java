package it.unitn.disi.personal.Utils;

public class WeatherData {

    private String description;
    private String data;
    private int trend;


    public WeatherData(String description, String data){

        this.description = description;
        this.data = data;
        this.trend = -2;
    }
    public WeatherData(String description, String data, int trend){

        this.description = description;
        this.data = data;
        this.trend = trend;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTrend() {
        return trend;
    }

    public void setTrend(int trend) {
        this.trend = trend;
    }
}

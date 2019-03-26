package it.unitn.disi.personal.Utils;

public class WeatherData {

    private String description;
    private String data;


    public WeatherData(String description, String data){

        this.description = description;
        this.data = data;
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
}

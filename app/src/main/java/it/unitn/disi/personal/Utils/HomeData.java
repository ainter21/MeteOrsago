package it.unitn.disi.personal.Utils;

public class HomeData {

    private int image;
    private String description;
    private String data;
    private int trend;

    public HomeData(int image, String description, String data) {
        this.image = image;
        this.description = description;
        this.data = data;
        trend = -2;
    }

    public HomeData(int image, String description, String data, int trend) {
        this.image = image;
        this.description = description;
        this.data = data;
        this.trend = trend;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

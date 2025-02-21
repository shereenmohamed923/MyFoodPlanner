package com.example.myfoodplanner.model.mealdetails;

public class IngredientDetails {
    private String name;
    private String measurement;
    private String thumbnail;

    public IngredientDetails(String name, String measurement, String thumbnail) {
        this.name = name;
        this.measurement = measurement;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

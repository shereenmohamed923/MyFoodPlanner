package com.example.myfoodplanner.model.area;

public class Area {
    private String strArea;

    public Area(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public String toString() {
        return "Area{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}

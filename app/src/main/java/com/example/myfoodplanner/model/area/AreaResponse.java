package com.example.myfoodplanner.model.area;

import java.util.List;

public class AreaResponse {
    private List<Area> meals;

    public AreaResponse(List<Area> areas) {
        meals = areas;
    }

    public List<Area> getAreas() {
        return meals;
    }

    public void setAreas(List<Area> areas) {
        meals = areas;
    }
}

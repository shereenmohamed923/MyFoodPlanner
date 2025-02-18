package com.example.myfoodplanner.model.filter;

import java.util.List;

public class FilterResponse {
    private List<Filter> meals;

    public FilterResponse(List<Filter> meals) {
        this.meals = meals;
    }

    public List<Filter> getMeals() {
        return meals;
    }

    public void setMeals(List<Filter> meals) {
        this.meals = meals;
    }
}

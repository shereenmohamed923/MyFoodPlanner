package com.example.myfoodplanner;

import java.util.List;

public class MealDetailsResponse {
    private List<MealDetails> meals;

    public MealDetailsResponse(List<MealDetails> meals) {
        this.meals = meals;
    }

    public List<MealDetails> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDetails> meals) {
        this.meals = meals;
    }
}

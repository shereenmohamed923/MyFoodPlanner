package com.example.myfoodplanner.model.ingredient;

import java.util.List;

public class IngredientResponse {
    private List<Ingredient> meals;
    public IngredientResponse(List<Ingredient> meals) {
        this.meals = meals;
    }
    public List<Ingredient> getMeals() {
        return meals;
    }
    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}

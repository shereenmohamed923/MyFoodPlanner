package com.example.myfoodplanner.meals.presenter;

public interface MealPresenter {
    void getMealsByCategory(String category);
    void getMealsByIngredient(String ingredient);
    void getMealsByArea(String area);
}

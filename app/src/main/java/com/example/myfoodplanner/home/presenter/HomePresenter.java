package com.example.myfoodplanner.home.presenter;

public interface HomePresenter {
    void getCategories();
    void getIngredients();
    void getAreas();
    void getMealDetails();
    void getMealsByCategory(String category);
    void getMealsByIngredient(String ingredient);
    void getMealsByArea(String area);
}

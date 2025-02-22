package com.example.myfoodplanner.mealdetails.presenter;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

public interface MealDetailsPresenter {
    void getMealById(String id);
    void AddToFav(MealDetails mealDetails);
    void checkIfMealIsFavourite(String mealId);
}

package com.example.myfoodplanner.mealdetails.presenter;

import com.example.myfoodplanner.model.mealdetails.MealDetails;


public interface MealDetailsPresenter {
    void getMealById(String id);
    void AddToFav(MealDetails mealDetails);
     void removeFromFav(String id);
    void checkIfMealIsFavourite(String mealId);
    void checkIfMealIsPlanned(String mealId);
    void AddToPlan(MealDetails mealDetails);
    void removeFromPlan(String id);
}

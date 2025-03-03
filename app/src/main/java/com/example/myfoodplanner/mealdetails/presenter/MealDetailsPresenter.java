package com.example.myfoodplanner.mealdetails.presenter;

import com.example.myfoodplanner.model.mealdetails.MealDetails;


public interface MealDetailsPresenter {
    void getMealById(String id);
    void addMealToFavourites(MealDetails meal);
    void removeMealFromFavourites(String mealId);
    void addMealToPlan(MealDetails meal, String chosenDate);
    void removeMealFromPlan(String mealId);
    void deleteFromFireStore(String mealId);
    void checkIfMealIsFavourite(String mealId);
    void checkIfMealIsPlanned(String mealId);
}

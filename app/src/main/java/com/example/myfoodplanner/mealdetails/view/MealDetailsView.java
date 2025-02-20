package com.example.myfoodplanner.mealdetails.view;

import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface MealDetailsView {
    void showMealDetails(List<MealDetails> mealDetails);
    void showErrorMsg(String msg);
}

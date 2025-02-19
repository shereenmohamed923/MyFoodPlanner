package com.example.myfoodplanner.meals.view;

import com.example.myfoodplanner.model.filter.Meal;

import java.util.List;

public interface MealView {
    void showFilteredList(List<Meal> filteredMeals);
    void showErrorMsg(String msg);
}

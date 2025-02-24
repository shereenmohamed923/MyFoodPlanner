package com.example.myfoodplanner.plan.presenter;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

public interface PlanPresenter {
    void removeMealFromPlan(String mealId);
    void getAllPlannedMeals(String chosenDate);

}

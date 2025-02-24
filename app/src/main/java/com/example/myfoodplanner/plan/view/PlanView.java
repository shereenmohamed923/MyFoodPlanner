package com.example.myfoodplanner.plan.view;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface PlanView {
    void showPlannedMeals(List<MealDetails> mealDetailsList);
    void showErrMsg(String msg);
}

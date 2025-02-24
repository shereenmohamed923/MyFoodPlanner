package com.example.myfoodplanner.FireBase.Backup;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

public interface BackupService {
    void addMealToFireStore(MealDetails meal);
    void deleteMealFromFireStore(String mealId);
    void restoreMealsFromFireStore(BackupCallBack callback);
}

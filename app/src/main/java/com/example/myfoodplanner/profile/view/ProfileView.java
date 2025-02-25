package com.example.myfoodplanner.profile.view;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface ProfileView {
    void successfulRestore(List<MealDetails> meals);
    void showMessage(String msg);
}

package com.example.myfoodplanner.Authentication.login.view;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface LoginView {
    void navigateToHome();
    void successfulRestore(List<MealDetails> meals);
    void showMessage(String msg);
}

package com.example.myfoodplanner.network.mealdetails;

import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface DetailsNetworkCallBack {
    void onRetrievedDetails(List<MealDetails> mealDetails);
    void onFailureResult(String errMsg);
}

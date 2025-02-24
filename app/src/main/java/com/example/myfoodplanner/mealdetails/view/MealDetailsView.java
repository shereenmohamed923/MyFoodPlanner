package com.example.myfoodplanner.mealdetails.view;

import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;

public interface MealDetailsView {
    void showMealDetails(List<MealDetails> mealDetails);
    void showErrorMsg(String msg);
    void updateFavouriteIcon(boolean isFavourite);
    void updatePlanIcon(boolean isPlanned);
}

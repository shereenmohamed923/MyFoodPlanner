package com.example.myfoodplanner.network.mealdetails;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import io.reactivex.rxjava3.core.Observable;

public interface MealDetailsRemoteDataSource {
    Observable<MealDetailsResponse> getMealById(String id);
}

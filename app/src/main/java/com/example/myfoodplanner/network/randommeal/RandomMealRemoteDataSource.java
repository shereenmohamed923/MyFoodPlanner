package com.example.myfoodplanner.network.randommeal;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import io.reactivex.rxjava3.core.Observable;

public interface RandomMealRemoteDataSource {
    Observable<MealDetailsResponse> getRandomMeal();
}

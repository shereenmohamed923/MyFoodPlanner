package com.example.myfoodplanner.database;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealDetailsLocalDataSource {
    Completable insertMealDetails(MealDetails mealDetails);
    Completable deleteMealDetails(MealDetails mealDetails);
    Flowable<List<MealDetails>> getFavouriteMealDetails();
    Single<Integer> isMealFavourite(String mealId);
}

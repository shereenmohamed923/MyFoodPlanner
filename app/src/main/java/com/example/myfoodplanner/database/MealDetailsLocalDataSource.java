package com.example.myfoodplanner.database;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealDetailsLocalDataSource {
    Completable insertMeal(MealDetails mealDetails);
    Single<Integer> isMealExists(String mealId);
    Completable addMealToFavourites(String mealId);
    Completable removeMealFromFavourites(String mealId);
    Single<Boolean> isMealFavourite(String mealId);
    Flowable<List<MealDetails>> getAllFavouriteMeals();
    Completable deleteMealIfNotPlannedOrFavourite(String mealId);
    Completable updateMealPlanDate(String mealId, String chosenDate);
    Completable removeMealFromPlanButKeepFavourite(String mealId);
    Completable deleteMealIfNotFavourite(String mealId);
    Single<Boolean> isMealPlanned(String mealId);
    Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate);
    Flowable<List<MealDetails>> getAllMeals();
}

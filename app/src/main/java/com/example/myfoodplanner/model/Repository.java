package com.example.myfoodplanner.model;

import android.content.Context;

import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.FireBase.Backup.BackupCallBack;
import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface Repository {
    Observable<CategoryResponse> getCategories();
    Observable<IngredientResponse> getIngredients();
    Observable<AreaResponse> getAreas();
    Observable<MealDetailsResponse> getRandomMeal();
    Observable<MealResponse> getMealsByCategory(String category);
    Observable<MealResponse> getMealsByIngredient(String ingredient);
    Observable<MealResponse> getMealsByArea(String area);
    Observable<MealDetailsResponse> getMealById(String id);
    boolean userExists();
    void signup(String email, String password, AuthCallback authCallback);
    void login(String email, String password, AuthCallback authCallback, Context context);
    void logout();
    Completable insertMeal(MealDetails mealDetails);
    Completable addMealToFavourites(MealDetails meal);
    Completable removeMealFromFavourites(String mealId);
    Completable addMealToPlan(MealDetails meal, String chosenDate);
    Completable removeMealFromPlan(String mealId);
    Single<Boolean> checkIfMealIsFavourite(String mealId);
    Single<Boolean> checkIfMealIsPlanned(String mealId);
    Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate);
    Flowable<List<MealDetails>> getAllFavouriteMeals();
    Flowable<List<MealDetails>> getAllMeals();
    void addMealToFireStore(List<MealDetails> meals);
    void restoreMealsFromFireStore(BackupCallBack callback);
}

package com.example.myfoodplanner.model;

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
    void signup(String email, String password, AuthCallback authCallback);
    void login(String email, String password, AuthCallback authCallback);
    Completable insertFavouriteMealDetails(MealDetails mealDetails);
    Completable deleteFavouriteMealDetails(String id);
    Flowable<List<MealDetails>> getFavouriteMealDetails();
    Single<Boolean> isMealFavourite(String mealId);
    Single<Boolean> isMealPlanned(String mealId);
    Completable insertMealToPlan(MealDetails mealDetails);
    Completable deleteMealFromPlan(String id);
    Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate);
//    void addMealToFireStore(MealDetails meal);
//    void deleteMealFromFireStore(String mealId);
//    void restoreMealsFromFireStore(BackupCallBack callback);
}

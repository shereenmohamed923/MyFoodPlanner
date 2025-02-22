package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
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
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
    Completable insertFavouriteMealDetails(MealDetails mealDetails);
    Completable deleteFavouriteMealDetails(MealDetails mealDetails);
    Flowable<List<MealDetails>> getFavouriteMealDetails();
    Single<Boolean> isMealFavourite(String mealId);
}

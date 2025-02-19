package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import io.reactivex.rxjava3.core.Observable;

public interface Repository {
    Observable<CategoryResponse> getCategories();
    Observable<IngredientResponse> getIngredients();
    Observable<AreaResponse> getAreas();
    Observable<MealDetailsResponse> getMealDetails();
    Observable<MealResponse> getMealsByCategory(String category);
    Observable<MealResponse> getMealsByIngredient(String ingredient);
    Observable<MealResponse> getMealsByArea(String area);
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
}

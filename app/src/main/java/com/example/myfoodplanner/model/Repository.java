package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.area.AreaNetworkCallBack;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.filter.FilterNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;
import com.example.myfoodplanner.network.mealdetails.DetailsNetworkCallBack;

public interface Repository {
    void getCategories(CategoryNetworkCallBack categoryNetworkCallBack);
    void getIngredients(IngredientNetworkCallBack ingredientNetworkCallBack);
    void getAreas(AreaNetworkCallBack areaNetworkCallBack);
    void getMealDetails(DetailsNetworkCallBack detailsNetworkCallBack);
    void getMealsByCategory(FilterNetworkCallBack filterNetworkCallBack, String category);
    void getMealsByIngredient(FilterNetworkCallBack filterNetworkCallBack, String ingredient);
    void getMealsByArea(FilterNetworkCallBack filterNetworkCallBack, String area);
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
}

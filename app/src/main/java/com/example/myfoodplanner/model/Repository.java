package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.area.AreaNetworkCallBack;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;

public interface Repository {
    void getCategories(CategoryNetworkCallBack categoryNetworkCallBack);
    void getIngredients(IngredientNetworkCallBack ingredientNetworkCallBack);
    void getAreas(AreaNetworkCallBack areaNetworkCallBack);
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
}

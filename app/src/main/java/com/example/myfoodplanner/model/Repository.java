package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;

public interface Repository {
    void getCategories(CategoryNetworkCallBack categoryNetworkCallBack);
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
}

package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;

public class RepositoryImpl implements Repository {
    CategoriesRemoteDataSource categoriesRemoteDataSource;
    AuthService authService;
    private static RepositoryImpl repo = null;
    private RepositoryImpl(CategoriesRemoteDataSource categoriesRemoteDataSource, AuthService authService) {
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.authService = authService;
    }
    public static RepositoryImpl getInstance(CategoriesRemoteDataSource categoriesRemoteDataSource, AuthService authService){
        if(repo == null){
            repo = new RepositoryImpl(categoriesRemoteDataSource,authService);
        }
        return repo;
    }

    @Override
    public void getCategories(CategoryNetworkCallBack categoryNetworkCallBack) {
        categoriesRemoteDataSource.makeNetworkCall(categoryNetworkCallBack);
    }

    @Override
    public void signup(String email, String password, FirebaseCallback firebaseCallback) {
        authService.signup(email, password, firebaseCallback);
    }

    @Override
    public void login(String email, String password, FirebaseCallback firebaseCallback) {
        authService.login(email, password, firebaseCallback);
    }
}

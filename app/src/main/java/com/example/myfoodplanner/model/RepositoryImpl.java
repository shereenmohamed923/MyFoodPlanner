package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSource;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;

public class RepositoryImpl implements Repository {
    CategoriesRemoteDataSource categoriesRemoteDataSource;
    AuthService authService;
    IngredientsRemoteDataSource ingredientsRemoteDataSource;
    private static RepositoryImpl repo = null;
    private RepositoryImpl(CategoriesRemoteDataSource categoriesRemoteDataSource, AuthService authService, IngredientsRemoteDataSource ingredientsRemoteDataSource) {
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.authService = authService;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
    }
    public static RepositoryImpl getInstance(CategoriesRemoteDataSource categoriesRemoteDataSource, AuthService authService, IngredientsRemoteDataSource ingredientsRemoteDataSource){
        if(repo == null){
            repo = new RepositoryImpl(categoriesRemoteDataSource,authService, ingredientsRemoteDataSource);
        }
        return repo;
    }

    @Override
    public void getCategories(CategoryNetworkCallBack categoryNetworkCallBack) {
        categoriesRemoteDataSource.makeNetworkCall(categoryNetworkCallBack);
    }
    public void getIngredients(IngredientNetworkCallBack ingredientNetworkCallBack) {
        ingredientsRemoteDataSource.makeNetworkCall(ingredientNetworkCallBack);
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

package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.network.area.AreaNetworkCallBack;
import com.example.myfoodplanner.network.area.AreaRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSource;
import com.example.myfoodplanner.network.mealdetails.DetailsNetworkCallBack;
import com.example.myfoodplanner.network.mealdetails.DetailsRemoteDataSource;

public class RepositoryImpl implements Repository {
    CategoriesRemoteDataSource categoriesRemoteDataSource;
    AuthService authService;
    IngredientsRemoteDataSource ingredientsRemoteDataSource;
    AreaRemoteDataSource areasRemoteDataSource;
    DetailsRemoteDataSource detailsRemoteDataSource;
    private static RepositoryImpl repo = null;
    private RepositoryImpl(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areasRemoteDataSource,
            DetailsRemoteDataSource detailsRemoteDataSource
    ) {
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.authService = authService;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
        this.areasRemoteDataSource = areasRemoteDataSource;
        this.detailsRemoteDataSource = detailsRemoteDataSource;
    }
    public static RepositoryImpl getInstance(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areaRemoteDataSource,
            DetailsRemoteDataSource detailsRemoteDataSource
    ){
        if(repo == null){
            repo = new RepositoryImpl(
                    categoriesRemoteDataSource,
                    authService,
                    ingredientsRemoteDataSource,
                    areaRemoteDataSource,
                    detailsRemoteDataSource
            );
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

    public void getAreas(AreaNetworkCallBack areaNetworkCallBack){
        areasRemoteDataSource.makeNetworkCall(areaNetworkCallBack);
    }

    @Override
    public void getMealDetails(DetailsNetworkCallBack detailsNetworkCallBack) {
        detailsRemoteDataSource.makeNetworkCall(detailsNetworkCallBack);
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

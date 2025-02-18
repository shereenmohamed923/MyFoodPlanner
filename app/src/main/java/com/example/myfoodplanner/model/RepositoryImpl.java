package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.network.area.AreaNetworkCallBack;
import com.example.myfoodplanner.network.area.AreaRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.FilterNetworkCallBack;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSource;
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
    CategoryFilterRemoteDataSource categoryFilterRemoteDataSource;
    IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource;
    AreaFilterRemoteDataSource areaFilterRemoteDataSource;
    private static RepositoryImpl repo = null;
    private RepositoryImpl(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areasRemoteDataSource,
            DetailsRemoteDataSource detailsRemoteDataSource,
            CategoryFilterRemoteDataSource categoryFilterRemoteDataSource,
            IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource,
                    AreaFilterRemoteDataSource areaFilterRemoteDataSource
    ) {
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.authService = authService;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
        this.areasRemoteDataSource = areasRemoteDataSource;
        this.detailsRemoteDataSource = detailsRemoteDataSource;
        this.categoryFilterRemoteDataSource = categoryFilterRemoteDataSource;
        this.ingredientFilterRemoteDataSource = ingredientFilterRemoteDataSource;
        this.areaFilterRemoteDataSource = areaFilterRemoteDataSource;
    }
    public static RepositoryImpl getInstance(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areaRemoteDataSource,
            DetailsRemoteDataSource detailsRemoteDataSource,
            CategoryFilterRemoteDataSource categoryFilterRemoteDataSource,
            IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource,
            AreaFilterRemoteDataSource areaFilterRemoteDataSource
    ){
        if(repo == null){
            repo = new RepositoryImpl(
                    categoriesRemoteDataSource,
                    authService,
                    ingredientsRemoteDataSource,
                    areaRemoteDataSource,
                    detailsRemoteDataSource,
                    categoryFilterRemoteDataSource,
                    ingredientFilterRemoteDataSource,
                    areaFilterRemoteDataSource
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
    public void getMealsByCategory(FilterNetworkCallBack filterNetworkCallBack, String category) {
        categoryFilterRemoteDataSource.categoryMakeNetworkCall(filterNetworkCallBack, category);
    }

    @Override
    public void getMealsByIngredient(FilterNetworkCallBack filterNetworkCallBack, String ingredient) {
        ingredientFilterRemoteDataSource.ingredientMakeNetworkCall(filterNetworkCallBack, ingredient);
    }

    @Override
    public void getMealsByArea(FilterNetworkCallBack filterNetworkCallBack, String area) {
        areaFilterRemoteDataSource.areaMakeNetworkCall(filterNetworkCallBack, area);
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

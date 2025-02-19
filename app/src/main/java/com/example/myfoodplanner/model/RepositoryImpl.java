package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;
import com.example.myfoodplanner.network.area.AreaRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSource;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSource;
import com.example.myfoodplanner.network.mealdetails.DetailsRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

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
    public Observable<CategoryResponse> getCategories() {
        return categoriesRemoteDataSource.getCategories();
    }

    @Override
    public Observable<IngredientResponse> getIngredients() {
        return ingredientsRemoteDataSource.getIngredients();
    }

    @Override
    public Observable<AreaResponse> getAreas() {
        return areasRemoteDataSource.getAreas();
    }

    @Override
    public Observable<MealDetailsResponse> getMealDetails() {
        return detailsRemoteDataSource.getMealDetails();
    }

    @Override
    public Observable<MealResponse> getMealsByCategory(String category) {
        return categoryFilterRemoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Observable<MealResponse> getMealsByIngredient(String ingredient) {
        return ingredientFilterRemoteDataSource.getMealsByIngredient(ingredient);
    }

    @Override
    public Observable<MealResponse> getMealsByArea(String area) {
        return areaFilterRemoteDataSource.getMealsByArea(area);
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

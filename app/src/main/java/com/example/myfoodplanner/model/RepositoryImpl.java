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
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSource;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;

public class RepositoryImpl implements Repository {
    CategoriesRemoteDataSource categoriesRemoteDataSource;
    AuthService authService;
    IngredientsRemoteDataSource ingredientsRemoteDataSource;
    AreaRemoteDataSource areasRemoteDataSource;
    RandomMealRemoteDataSource randomMealRemoteDataSource;
    CategoryFilterRemoteDataSource categoryFilterRemoteDataSource;
    IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource;
    AreaFilterRemoteDataSource areaFilterRemoteDataSource;
    MealDetailsRemoteDataSource mealDetailsRemoteDataSource;
    private static RepositoryImpl repo = null;
    private RepositoryImpl(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areasRemoteDataSource,
            RandomMealRemoteDataSource randomMealRemoteDataSource,
            CategoryFilterRemoteDataSource categoryFilterRemoteDataSource,
            IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource,
            AreaFilterRemoteDataSource areaFilterRemoteDataSource,
            MealDetailsRemoteDataSource mealDetailsRemoteDataSource
    ) {
        this.categoriesRemoteDataSource = categoriesRemoteDataSource;
        this.authService = authService;
        this.ingredientsRemoteDataSource = ingredientsRemoteDataSource;
        this.areasRemoteDataSource = areasRemoteDataSource;
        this.randomMealRemoteDataSource = randomMealRemoteDataSource;
        this.categoryFilterRemoteDataSource = categoryFilterRemoteDataSource;
        this.ingredientFilterRemoteDataSource = ingredientFilterRemoteDataSource;
        this.areaFilterRemoteDataSource = areaFilterRemoteDataSource;
        this.mealDetailsRemoteDataSource = mealDetailsRemoteDataSource;
    }
    public static RepositoryImpl getInstance(
            CategoriesRemoteDataSource categoriesRemoteDataSource,
            AuthService authService,
            IngredientsRemoteDataSource ingredientsRemoteDataSource,
            AreaRemoteDataSource areaRemoteDataSource,
            RandomMealRemoteDataSource randomMealRemoteDataSource,
            CategoryFilterRemoteDataSource categoryFilterRemoteDataSource,
            IngredientFilterRemoteDataSource ingredientFilterRemoteDataSource,
            AreaFilterRemoteDataSource areaFilterRemoteDataSource,
            MealDetailsRemoteDataSource mealDetailsRemoteDataSource
    ){
        if(repo == null){
            repo = new RepositoryImpl(
                    categoriesRemoteDataSource,
                    authService,
                    ingredientsRemoteDataSource,
                    areaRemoteDataSource,
                    randomMealRemoteDataSource,
                    categoryFilterRemoteDataSource,
                    ingredientFilterRemoteDataSource,
                    areaFilterRemoteDataSource,
                    mealDetailsRemoteDataSource
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
    public Observable<MealDetailsResponse> getRandomMeal() {
        return randomMealRemoteDataSource.getRandomMeal();
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
    public Observable<MealDetailsResponse> getMealById(String id) {
        return mealDetailsRemoteDataSource.getMealById(id);
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

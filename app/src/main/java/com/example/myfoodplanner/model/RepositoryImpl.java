package com.example.myfoodplanner.model;

import com.example.myfoodplanner.Authentication.network.AuthService;
import com.example.myfoodplanner.Authentication.network.FirebaseCallback;
import com.example.myfoodplanner.database.MealDetailsLocalDataSource;
import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;
import com.example.myfoodplanner.network.area.AreaRemoteDataSource;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSource;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSource;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSource;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSource;
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSource;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

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
    MealDetailsLocalDataSource mealDetailsLocalDataSource;
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
            MealDetailsRemoteDataSource mealDetailsRemoteDataSource,
            MealDetailsLocalDataSource mealDetailsLocalDataSource
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
        this.mealDetailsLocalDataSource = mealDetailsLocalDataSource;
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
            MealDetailsRemoteDataSource mealDetailsRemoteDataSource,
            MealDetailsLocalDataSource mealDetailsLocalDataSource
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
                    mealDetailsRemoteDataSource,
                    mealDetailsLocalDataSource
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

    @Override
    public Completable insertFavouriteMealDetails(MealDetails mealDetails) {
        return mealDetailsLocalDataSource.insertMealDetails(mealDetails);
    }

    @Override
    public Completable deleteFavouriteMealDetails(MealDetails mealDetails) {
        return mealDetailsLocalDataSource.deleteMealDetails(mealDetails);
    }

    @Override
    public Flowable<List<MealDetails>> getFavouriteMealDetails() {
        return mealDetailsLocalDataSource.getFavouriteMealDetails();
    }

    @Override
    public Single<Boolean> isMealFavourite(String mealId) {
        return mealDetailsLocalDataSource.isMealFavourite(mealId)
                .map(count -> count > 0);
    }
}

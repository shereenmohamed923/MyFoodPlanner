package com.example.myfoodplanner.model;

import com.example.myfoodplanner.FireBase.Authentication.AuthService;
import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.FireBase.Backup.BackupCallBack;
import com.example.myfoodplanner.FireBase.Backup.BackupService;
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
    BackupService backupService;
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
            MealDetailsLocalDataSource mealDetailsLocalDataSource,
            BackupService backupService
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
        this.backupService = backupService;
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
            MealDetailsLocalDataSource mealDetailsLocalDataSource,
            BackupService backupService
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
                    mealDetailsLocalDataSource,
                    backupService
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
    public void signup(String email, String password, AuthCallback authCallback) {
        authService.signup(email, password, authCallback);
    }

    @Override
    public void login(String email, String password, AuthCallback authCallback) {
        authService.login(email, password, authCallback);
    }

    @Override
    public Completable insertFavouriteMealDetails(MealDetails mealDetails) {
        return mealDetailsLocalDataSource.insertMealDetails(mealDetails);
    }

    @Override
    public Completable deleteFavouriteMealDetails(String id) {
        return mealDetailsLocalDataSource.deleteMealDetails(id);
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

    @Override
    public Single<Boolean> isMealPlanned(String mealId) {
        return mealDetailsLocalDataSource.isMealPlanned(mealId)
                .map(count -> count > 0);
    }

    @Override
    public Completable insertMealToPlan(MealDetails mealDetails) {
        backupService.addMealToFireStore(mealDetails);
        return mealDetailsLocalDataSource.insertMealToPlan(mealDetails);
    }

    @Override
    public Completable deleteMealFromPlan(String id) {
        backupService.deleteMealFromFireStore(id);
        return mealDetailsLocalDataSource.deleteMealFromPlan(id);
    }

    @Override
    public Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate) {
        return mealDetailsLocalDataSource.getAllPlannedMeals(chosenDate);
    }

//    @Override
//    public void addMealToFireStore(MealDetails meal) {
//
//    }
//
//    @Override
//    public void deleteMealFromFireStore(String mealId) {
//
//    }
//
//    @Override
//    public void restoreMealsFromFireStore(BackupCallBack callback) {
//        backupService.restoreMealsFromFireStore(callback);
//    }
}

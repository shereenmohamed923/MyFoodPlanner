package com.example.myfoodplanner.model;

import android.content.Context;

import com.example.myfoodplanner.FireBase.Authentication.AuthService;
import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.FireBase.Backup.AddCallBack;
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
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    public boolean userExists() {
        return authService.userExists();
    }

    @Override
    public void signup(String email, String password, AuthCallback authCallback) {
        authService.signup(email, password, authCallback);
    }

    @Override
    public void login(String email, String password, AuthCallback authCallback, Context context) {
        authService.login(email, password, authCallback, context);
    }

    @Override
    public Completable insertMeal(MealDetails mealDetails){
        return mealDetailsLocalDataSource.insertMeal(mealDetails);
    }

    @Override
    public Completable addMealToFavourites(MealDetails meal) {
        return mealDetailsLocalDataSource.isMealExists(meal.getIdMeal())
                .flatMapCompletable(count -> {
                    if (count > 0) {
                        return mealDetailsLocalDataSource.addMealToFavourites(meal.getIdMeal());
                    } else {
                        meal.setFavourite(true);
                        return mealDetailsLocalDataSource.insertMeal(meal);
                    }
                });
    }

    @Override
    public Completable removeMealFromFavourites(String mealId) {
        return mealDetailsLocalDataSource.removeMealFromFavourites(mealId)
                .andThen(mealDetailsLocalDataSource.deleteMealIfNotPlannedOrFavourite(mealId));
    }

    @Override
    public Completable addMealToPlan(MealDetails meal, String chosenDate) {
        return mealDetailsLocalDataSource.isMealExists(meal.getIdMeal())
                .flatMapCompletable(count -> {
                    if (count > 0) {
                        return mealDetailsLocalDataSource.updateMealPlanDate(meal.getIdMeal(), chosenDate);
                    } else {
                        meal.setDate(chosenDate);
                        return mealDetailsLocalDataSource.insertMeal(meal);
                    }
                });
    }

    @Override
    public Completable removeMealFromPlan(String mealId) {
        return mealDetailsLocalDataSource.removeMealFromPlanButKeepFavourite(mealId)
                .andThen(mealDetailsLocalDataSource.deleteMealIfNotFavourite(mealId));
    }

    @Override
    public Single<Boolean> checkIfMealIsFavourite(String mealId) {
        return mealDetailsLocalDataSource.isMealFavourite(mealId);
    }

    @Override
    public Single<Boolean> checkIfMealIsPlanned(String mealId) {
        return mealDetailsLocalDataSource.isMealPlanned(mealId);
    }

    @Override
    public Flowable<List<MealDetails>> getAllFavouriteMeals() {
        return mealDetailsLocalDataSource.getAllFavouriteMeals();
    }
    @Override
    public Flowable<List<MealDetails>> getAllMeals(){
        return mealDetailsLocalDataSource.getAllMeals();
    }

    @Override
    public Completable ClearDataBase() {
        return mealDetailsLocalDataSource.deleteAll();
    }

    @Override
    public void addMealToFireStore(List<MealDetails> meals, AddCallBack addCallBack) {
        backupService.addMealToFireStore(meals,addCallBack);
    }

    @Override
    public void restoreMealsFromFireStore(BackupCallBack callback) {
        backupService.restoreMealsFromFireStore(callback);
    }

    @Override
    public void signOut() {
        backupService.signOut();
    }

    @Override
    public Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate) {
        return mealDetailsLocalDataSource.getAllPlannedMeals(chosenDate);
    }

}

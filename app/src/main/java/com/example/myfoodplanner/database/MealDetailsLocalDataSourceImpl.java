package com.example.myfoodplanner.database;

import android.content.Context;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealDetailsLocalDataSourceImpl implements MealDetailsLocalDataSource{
 private static MealDetailsLocalDataSourceImpl localDataSource = null;
 private MealDetailsDao dao;
 private MealDetailsLocalDataSourceImpl(Context context){
     MyDataBase dataBase = MyDataBase.getInstance(context.getApplicationContext());
     dao = dataBase.getMealDetailsDao();
     Flowable<List<MealDetails>> FavouriteProducts = dao.getAllFavouriteMeals();
 }
 public static MealDetailsLocalDataSourceImpl getInstance(Context context){
     if(localDataSource == null){
         localDataSource = new MealDetailsLocalDataSourceImpl(context);
     }
     return localDataSource;
 }
    @Override
    public Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate) {
        return dao.getAllPlannedMeals(chosenDate);
    }

    @Override
    public Completable insertMeal(MealDetails mealDetails) {
        return dao.insertMeal(mealDetails);
    }

    @Override
    public Single<Integer> isMealExists(String mealId) {
        return dao.isMealExists(mealId);
    }

    @Override
    public Completable addMealToFavourites(String mealId) {
        return dao.addMealToFavourites(mealId);
    }

    @Override
    public Completable removeMealFromFavourites(String mealId) {
        return dao.removeMealFromFavourites(mealId);
    }

    @Override
    public Single<Boolean> isMealFavourite(String mealId) {
        return dao.isMealFavourite(mealId);
    }

    @Override
    public Flowable<List<MealDetails>> getAllFavouriteMeals() {
        return dao.getAllFavouriteMeals();
    }

    @Override
    public Completable deleteMealIfNotPlannedOrFavourite(String mealId) {
        return dao.deleteMealIfNotPlannedOrFavourite(mealId);
    }

    @Override
    public Completable updateMealPlanDate(String mealId, String chosenDate) {
        return dao.updateMealPlanDate(mealId, chosenDate);
    }

    @Override
    public Completable removeMealFromPlanButKeepFavourite(String mealId) {
        return dao.removeMealFromPlanButKeepFavourite(mealId);
    }

    @Override
    public Completable deleteMealIfNotFavourite(String mealId) {
        return dao.deleteMealIfNotFavourite(mealId);
    }

    @Override
    public Single<Boolean> isMealPlanned(String mealId) {
        return dao.isMealPlanned(mealId);
    }


}

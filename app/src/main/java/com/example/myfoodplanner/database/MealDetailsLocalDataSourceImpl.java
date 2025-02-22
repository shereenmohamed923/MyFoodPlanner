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
    public Completable insertMealDetails(MealDetails mealDetails) {
        return dao.insertMealToFavourite(mealDetails);
    }

    @Override
    public Completable deleteMealDetails(MealDetails mealDetails) {
        return dao.deleteMealFromFavourite(mealDetails);
    }

    @Override
    public Flowable<List<MealDetails>> getFavouriteMealDetails() {
        return dao.getAllFavouriteMeals();
    }

    @Override
    public Single<Integer> isMealFavourite(String mealId) {
        return dao.isMealFavourite(mealId);
    }
}

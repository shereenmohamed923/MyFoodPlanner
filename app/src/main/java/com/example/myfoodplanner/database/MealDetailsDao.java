package com.example.myfoodplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMealToFavourite(MealDetails mealDetails);

    @Delete
    Completable deleteMealFromFavourite(MealDetails mealDetails);

    @Query("select * from meal_details_table")
    Flowable<List<MealDetails>> getAllFavouriteMeals();

    @Query("SELECT COUNT(*) FROM meal_details_table WHERE idMeal = :mealId")
    Single<Integer> isMealFavourite(String mealId);
}

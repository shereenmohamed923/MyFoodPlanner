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

    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 1")
    Completable deleteMealFromFavourite(String mealId);

    @Query("select * from meal_details_table WHERE isFavourite = 1")
    Flowable<List<MealDetails>> getAllFavouriteMeals();

    @Query("SELECT COUNT(*) FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 1 AND date = '' ")
    Single<Integer> isMealFavourite(String mealId);

    @Query("SELECT COUNT(*) FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 0 AND date != '' ")
    Single<Integer> isMealPlanned(String mealId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMealToPlan(MealDetails mealDetails);

    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 0")
    Completable deleteMealFromPlan(String mealId);

    @Query("select * from meal_details_table WHERE date IS NOT NULL")
    Flowable<List<MealDetails>> getAllPlannedMeals();
}

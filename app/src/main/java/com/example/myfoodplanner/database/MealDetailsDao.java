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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(MealDetails mealDetails);
    @Query("SELECT * FROM meal_details_table")
    Flowable<List<MealDetails>> getAllMeals();
    @Query("UPDATE meal_details_table SET isFavourite = 1 WHERE idMeal = :mealId")
    Completable addMealToFavourites(String mealId);
    @Query("UPDATE meal_details_table SET isFavourite = 0 WHERE idMeal = :mealId")
    Completable removeMealFromFavourites(String mealId);
    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND (date IS NULL OR date = '') AND isFavourite = 0")
    Completable deleteMealIfNotPlannedOrFavourite(String mealId);
    @Query("UPDATE meal_details_table SET date = :chosenDate WHERE idMeal = :mealId")
    Completable updateMealPlanDate(String mealId, String chosenDate);
    @Query("UPDATE meal_details_table SET date = NULL WHERE idMeal = :mealId AND isFavourite = 1")
    Completable removeMealFromPlanButKeepFavourite(String mealId);
    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 0")
    Completable deleteMealIfNotFavourite(String mealId);
    @Query("SELECT COUNT(*) FROM meal_details_table WHERE idMeal = :mealId")
    Single<Integer> isMealExists(String mealId);
    @Query("SELECT isFavourite FROM meal_details_table WHERE idMeal = :mealId")
    Single<Boolean> isMealFavourite(String mealId);
    @Query("SELECT CASE WHEN date IS NOT NULL AND date != '' THEN 1 ELSE 0 END FROM meal_details_table WHERE idMeal = :mealId")
    Single<Boolean> isMealPlanned(String mealId);
    @Query("SELECT * FROM meal_details_table WHERE isFavourite = 1")
    Flowable<List<MealDetails>> getAllFavouriteMeals();
    @Query("SELECT * FROM meal_details_table WHERE date = :chosenDate")
    Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate);
    @Query("DELETE FROM meal_details_table")
    Completable deleteAll();
}

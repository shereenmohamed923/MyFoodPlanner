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
    // Insert meal (only if it doesn't exist)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(MealDetails mealDetails);

    // Add meal to Favourites
    @Query("UPDATE meal_details_table SET isFavourite = 1 WHERE idMeal = :mealId")
    Completable addMealToFavourites(String mealId);

    // Remove meal from Favourites (but don't delete if planned)
    @Query("UPDATE meal_details_table SET isFavourite = 0 WHERE idMeal = :mealId")
    Completable removeMealFromFavourites(String mealId);

    // Delete meal if it's neither Favourite nor Planned
    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND (date IS NULL OR date = '') AND isFavourite = 0")
    Completable deleteMealIfNotPlannedOrFavourite(String mealId);

    // Add meal to Plan
    @Query("UPDATE meal_details_table SET date = :chosenDate WHERE idMeal = :mealId")
    Completable updateMealPlanDate(String mealId, String chosenDate);

    // Remove meal from Plan (but don't delete if Favourite)
    @Query("UPDATE meal_details_table SET date = NULL WHERE idMeal = :mealId AND isFavourite = 1")
    Completable removeMealFromPlanButKeepFavourite(String mealId);

    // Delete meal if not Favourite
    @Query("DELETE FROM meal_details_table WHERE idMeal = :mealId AND isFavourite = 0")
    Completable deleteMealIfNotFavourite(String mealId);

    // Check if meal exists
    @Query("SELECT COUNT(*) FROM meal_details_table WHERE idMeal = :mealId")
    Single<Integer> isMealExists(String mealId);

    // ✅ Check if meal is Favourite
    @Query("SELECT isFavourite FROM meal_details_table WHERE idMeal = :mealId")
    Single<Boolean> isMealFavourite(String mealId);

    // ✅ Check if meal is Planned (has a date set)
    @Query("SELECT CASE WHEN date IS NOT NULL AND date != '' THEN 1 ELSE 0 END FROM meal_details_table WHERE idMeal = :mealId")
    Single<Boolean> isMealPlanned(String mealId);

    // Get all Favourite meals
    @Query("SELECT * FROM meal_details_table WHERE isFavourite = 1")
    Flowable<List<MealDetails>> getAllFavouriteMeals();

    // Get all Planned meals by date
    @Query("SELECT * FROM meal_details_table WHERE date = :chosenDate")
    Flowable<List<MealDetails>> getAllPlannedMeals(String chosenDate);
}

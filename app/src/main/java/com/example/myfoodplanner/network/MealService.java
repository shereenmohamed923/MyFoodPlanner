package com.example.myfoodplanner.network;

import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("categories.php")
    Call<CategoryResponse> getMealsCategories();

    @GET("random.php") //recipe of the day
    Call<MealDetailsResponse> getRandomMeal();

    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on category)
    Call<MealDetailsResponse> getMealsByCategory(@Query("c") String category);
    @GET("list.php?i=list") // idIngredient - strIngredient
    Call<IngredientResponse> getIngredient();
}

package com.example.myfoodplanner.network;

import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.FilterResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php") //recipe of the day
    Call<MealDetailsResponse> getRandomMeal();
    @GET("categories.php")
    Call<CategoryResponse> getCategories();
    @GET("list.php?i=list") // idIngredient - strIngredient
    Call<IngredientResponse> getIngredients();
    @GET("list.php?a=list") // strArea
    Call<AreaResponse> getAreas();
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on category)
    Call<FilterResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on ingredient)
    Call<FilterResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on area)
    Call<FilterResponse> getMealsByArea(@Query("a") String area);

}

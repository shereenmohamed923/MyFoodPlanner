package com.example.myfoodplanner.network;

import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php") //recipe of the day
    Observable<MealDetailsResponse> getRandomMeal();
    @GET("categories.php")
    Observable<CategoryResponse> getCategories();
    @GET("list.php?i=list") // idIngredient - strIngredient
    Observable<IngredientResponse> getIngredients();
    @GET("list.php?a=list") // strArea
    Observable<AreaResponse> getAreas();
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on category)
    Observable<MealResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on ingredient)
    Observable<MealResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")// idMeal - strMeal - strMealThumb (based on area)
    Observable<MealResponse> getMealsByArea(@Query("a") String area);
    @GET("lookup.php")
    Observable<MealDetailsResponse> getMealById(@Query("i") String id);

}

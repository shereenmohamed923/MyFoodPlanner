package com.example.myfoodplanner;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealCategoryService {
    @GET("categories.php")
    Call<MealCategoryResponse> getMealsCategories();
}

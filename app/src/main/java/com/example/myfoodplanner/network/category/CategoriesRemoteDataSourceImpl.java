package com.example.myfoodplanner.network.category;

import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.network.MealService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//singleton class
public class CategoriesRemoteDataSourceImpl implements CategoriesRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static CategoriesRemoteDataSourceImpl client = null;
    private MealService mealService;

    private CategoriesRemoteDataSourceImpl(){
        //retrofit initialization and creating
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static CategoriesRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new CategoriesRemoteDataSourceImpl();
        }
        return client;
    }
    @Override
    public void makeNetworkCall(CategoryNetworkCallBack categoryNetworkCallBack) {
        if (mealService == null) {
            categoryNetworkCallBack.onFailureResult("MealService not initialized");
            return;
        }
        //call and enqueue
        Call<CategoryResponse> call = mealService.getMealsCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                //the presenter will implement what happens onSuccess
                if (response.isSuccessful() && response.body() != null) {
                    // Ensure response body is valid before accessing
                    categoryNetworkCallBack.onRetrievedCategory(response.body().getCategories());
                } else {
                    categoryNetworkCallBack.onFailureResult("Response unsuccessful or empty");
                }
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                //the presenter will implement what happens onFailure
                categoryNetworkCallBack.onFailureResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
}

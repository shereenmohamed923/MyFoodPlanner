package com.example.myfoodplanner.network.filter;

import android.util.Log;

import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.network.MealService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryFilterRemoteDataSourceImpl implements CategoryFilterRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static CategoryFilterRemoteDataSourceImpl client = null;
    private MealService mealService;
    private CategoryFilterRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static CategoryFilterRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new CategoryFilterRemoteDataSourceImpl();
        }
        return client;
    }
    @Override
    public void categoryMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String category) {
        if (mealService == null) {
            filterNetworkCallBack.onFailureResult("MealService not initialized");
            return;
        }
        Call<MealResponse> call = mealService.getMealsByCategory(category);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("jj", "onResponse: "+response.body().getMeals().size());
                    filterNetworkCallBack.onRetrievedFilter(response.body().getMeals());
                } else {
                    Log.d("jj", "onResponse: "+response.body());
                    filterNetworkCallBack.onFailureResult("Response unsuccessful or empty");
                }
            }
            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.d("jj", "onResponse: "+throwable.getMessage());
                filterNetworkCallBack.onFailureResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
}

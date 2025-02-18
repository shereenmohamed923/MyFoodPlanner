package com.example.myfoodplanner.network.filter;

import android.util.Log;

import com.example.myfoodplanner.model.filter.FilterResponse;
import com.example.myfoodplanner.network.MealService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaFilterRemoteDataSourceImpl implements AreaFilterRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static AreaFilterRemoteDataSourceImpl client = null;
    private MealService mealService;
    private AreaFilterRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static AreaFilterRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new AreaFilterRemoteDataSourceImpl();
        }
        return client;
    }
    @Override
    public void areaMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String area) {
        if (mealService == null) {
            filterNetworkCallBack.onFailureResult("MealService not initialized");
            return;
        }
        Call<FilterResponse> call = mealService.getMealsByArea(area);
        call.enqueue(new Callback<FilterResponse>() {
            @Override
            public void onResponse(Call<FilterResponse> call, Response<FilterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("jj", "onResponse: "+response.body().getMeals().size());
                    filterNetworkCallBack.onRetrievedFilter(response.body().getMeals());
                } else {
                    Log.d("jj", "onResponse: "+response.body());
                    filterNetworkCallBack.onFailureResult("Response unsuccessful or empty");
                }
            }
            @Override
            public void onFailure(Call<FilterResponse> call, Throwable throwable) {
                Log.d("jj", "onResponse: "+throwable.getMessage());
                filterNetworkCallBack.onFailureResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
}

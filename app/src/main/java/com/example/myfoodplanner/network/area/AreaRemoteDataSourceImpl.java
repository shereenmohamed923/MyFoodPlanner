package com.example.myfoodplanner.network.area;

import android.util.Log;

import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.network.MealService;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaRemoteDataSourceImpl implements AreaRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static AreaRemoteDataSourceImpl client = null;
    private MealService mealService;
    private AreaRemoteDataSourceImpl(){
        //retrofit initialization and creating
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static AreaRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new AreaRemoteDataSourceImpl();
        }
        return client;
    }
    @Override
    public void makeNetworkCall(AreaNetworkCallBack areaNetworkCallBack) {
        if (mealService == null) {
            areaNetworkCallBack.onFailureResult("MealService not initialized");
            return;
        }
        //call and enqueue
        Call<AreaResponse> call = mealService.getAreas();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                //the presenter will implement what happens onSuccess
                if (response.isSuccessful() && response.body() != null) {
                    // Ensure response body is valid before accessing
                    Log.d("tag", "onResponsea area: "+response.body().getAreas().size());
                    areaNetworkCallBack.onRetrievedArea(response.body().getAreas());
                } else {
                    areaNetworkCallBack.onFailureResult("Response unsuccessful or empty");
                }
            }
            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                //the presenter will implement what happens onFailure
                areaNetworkCallBack.onFailureResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
}

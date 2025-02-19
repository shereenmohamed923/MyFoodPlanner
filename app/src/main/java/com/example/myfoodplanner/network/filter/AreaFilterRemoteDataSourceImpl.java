package com.example.myfoodplanner.network.filter;

import android.util.Log;

import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<MealResponse> getMealsByArea(String area) {
        return mealService.getMealsByArea(area)
                .subscribeOn(Schedulers.io());
    }
}

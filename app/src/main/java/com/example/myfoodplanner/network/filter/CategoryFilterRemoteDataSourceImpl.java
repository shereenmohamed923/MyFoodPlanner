package com.example.myfoodplanner.network.filter;

import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<MealResponse> getMealsByCategory(String category) {
        return mealService.getMealsByCategory(category)
                .subscribeOn(Schedulers.io());
    }
}

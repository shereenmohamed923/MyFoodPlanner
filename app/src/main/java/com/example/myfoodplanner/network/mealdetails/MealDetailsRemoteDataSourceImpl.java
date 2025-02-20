package com.example.myfoodplanner.network.mealdetails;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;
import com.example.myfoodplanner.network.MealService;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSourceImpl;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDetailsRemoteDataSourceImpl implements MealDetailsRemoteDataSource{
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealDetailsRemoteDataSourceImpl client = null;
    private MealService mealService;
    private MealDetailsRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static MealDetailsRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new MealDetailsRemoteDataSourceImpl();
        }
        return client;
    }
    @Override
    public Observable<MealDetailsResponse> getMealById(String id) {
        return mealService.getMealById(id)
                .subscribeOn(Schedulers.io());
    }
}

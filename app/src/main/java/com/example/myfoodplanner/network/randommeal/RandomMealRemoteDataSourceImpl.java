package com.example.myfoodplanner.network.randommeal;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomMealRemoteDataSourceImpl implements RandomMealRemoteDataSource {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static RandomMealRemoteDataSourceImpl client = null;
    private MealService mealService;
    private RandomMealRemoteDataSourceImpl(){
        //retrofit initialization and creating
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static RandomMealRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new RandomMealRemoteDataSourceImpl();
        }
        return client;
    }

    @Override
    public Observable<MealDetailsResponse> getRandomMeal() {
        return mealService.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }
}

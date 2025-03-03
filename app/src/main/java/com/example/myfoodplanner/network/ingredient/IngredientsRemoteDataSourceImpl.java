package com.example.myfoodplanner.network.ingredient;

import android.util.Log;

import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientsRemoteDataSourceImpl implements IngredientsRemoteDataSource{
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static IngredientsRemoteDataSourceImpl client = null;
    private MealService mealService;
    private IngredientsRemoteDataSourceImpl(){
        //retrofit initialization and creating
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static IngredientsRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new IngredientsRemoteDataSourceImpl();
        }
        return client;
    }

    @Override
    public Observable<IngredientResponse> getIngredients() {
        return mealService.getIngredients()
                .subscribeOn(Schedulers.io());
    }
}

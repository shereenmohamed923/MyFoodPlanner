package com.example.myfoodplanner.network.area;

import android.util.Log;

import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.network.MealService;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<AreaResponse> getAreas() {
        return mealService.getAreas()
                .subscribeOn(Schedulers.io());
    }
}

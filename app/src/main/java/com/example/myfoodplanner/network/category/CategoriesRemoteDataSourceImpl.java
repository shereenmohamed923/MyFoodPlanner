package com.example.myfoodplanner.network.category;

import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<CategoryResponse> getCategories() {
        return mealService.getCategories()
                .subscribeOn(Schedulers.io());
    }
}

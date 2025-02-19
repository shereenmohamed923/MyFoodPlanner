package com.example.myfoodplanner.network.mealdetails;

import android.util.Log;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;
import com.example.myfoodplanner.network.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsRemoteDataSourceImpl implements DetailsRemoteDataSource{
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static DetailsRemoteDataSourceImpl client = null;
    private MealService mealService;
    private DetailsRemoteDataSourceImpl(){
        //retrofit initialization and creating
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static DetailsRemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new DetailsRemoteDataSourceImpl();
        }
        return client;
    }
//    @Override
//    public void makeNetworkCall(DetailsNetworkCallBack detailsNetworkCallBack) {
//        if (mealService == null) {
//            detailsNetworkCallBack.onFailureResult("MealService not initialized");
//            return;
//        }
//        Call<MealDetailsResponse> call = mealService.getRandomMeal();
//        call.enqueue(new Callback<MealDetailsResponse>() {
//            @Override
//            public void onResponse(Call<MealDetailsResponse> call, Response<MealDetailsResponse> response) {
//                //the presenter will implement what happens onSuccess
//                if (response.isSuccessful() && response.body() != null) {
//                    // Ensure response body is valid before accessing
//                    Log.d("jj", "onResponse: "+response.body().getMeals().size());
//                    detailsNetworkCallBack.onRetrievedDetails(response.body().getMeals());
//                } else {
//                    Log.d("jj", "onResponse: "+response.body().getMeals().size());
//                    detailsNetworkCallBack.onFailureResult("Response unsuccessful or empty");
//                }
//            }
//            @Override
//            public void onFailure(Call<MealDetailsResponse> call, Throwable throwable) {
//                Log.d("jj", "onResponse: "+throwable.getMessage());
//                //the presenter will implement what happens onFailure
//                detailsNetworkCallBack.onFailureResult(throwable.getMessage());
//                throwable.printStackTrace();
//            }
//        });
//    }

    @Override
    public Observable<MealDetailsResponse> getMealDetails() {
        return mealService.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }
}

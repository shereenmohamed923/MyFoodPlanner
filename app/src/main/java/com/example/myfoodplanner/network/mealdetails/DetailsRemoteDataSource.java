package com.example.myfoodplanner.network.mealdetails;

import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import io.reactivex.rxjava3.core.Observable;

public interface DetailsRemoteDataSource {
//    void makeNetworkCall(DetailsNetworkCallBack detailsNetworkCallBack);
    Observable<MealDetailsResponse> getMealDetails();
}

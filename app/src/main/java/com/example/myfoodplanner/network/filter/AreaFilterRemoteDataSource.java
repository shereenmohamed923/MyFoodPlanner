package com.example.myfoodplanner.network.filter;

import com.example.myfoodplanner.model.filter.MealResponse;

import io.reactivex.rxjava3.core.Observable;

public interface AreaFilterRemoteDataSource {
//    void areaMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String area);
    Observable<MealResponse> getMealsByArea(String area);
}

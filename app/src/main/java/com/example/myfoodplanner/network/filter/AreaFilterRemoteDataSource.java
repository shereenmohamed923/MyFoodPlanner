package com.example.myfoodplanner.network.filter;

import com.example.myfoodplanner.model.filter.MealResponse;

import io.reactivex.rxjava3.core.Observable;

public interface AreaFilterRemoteDataSource {
    Observable<MealResponse> getMealsByArea(String area);
}

package com.example.myfoodplanner.network.filter;

import com.example.myfoodplanner.model.filter.Meal;

import java.util.List;

public interface FilterNetworkCallBack {
    void onRetrievedFilter(List<Meal> meals);
    void onFailureResult(String errMsg);
}

package com.example.myfoodplanner.network.filter;

import com.example.myfoodplanner.model.filter.Filter;

import java.util.List;

public interface FilterNetworkCallBack {
    void onRetrievedFilter(List<Filter> filters);
    void onFailureResult(String errMsg);
}

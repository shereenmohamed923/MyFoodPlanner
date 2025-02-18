package com.example.myfoodplanner.network.filter;

public interface CategoryFilterRemoteDataSource {
    void categoryMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String category);
}

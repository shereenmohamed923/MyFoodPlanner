package com.example.myfoodplanner.network.filter;

public interface AreaFilterRemoteDataSource {
    void areaMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String area);
}

package com.example.myfoodplanner.network.area;

import com.example.myfoodplanner.model.area.AreaResponse;

import io.reactivex.rxjava3.core.Observable;

public interface AreaRemoteDataSource {
//    void makeNetworkCall(AreaNetworkCallBack areaNetworkCallBack);
    Observable<AreaResponse> getAreas();
}

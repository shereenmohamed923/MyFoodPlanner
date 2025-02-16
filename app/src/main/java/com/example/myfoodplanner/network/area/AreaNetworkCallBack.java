package com.example.myfoodplanner.network.area;

import com.example.myfoodplanner.model.area.Area;

import java.util.List;

public interface AreaNetworkCallBack {
    void onRetrievedArea(List<Area> areas);
    void onFailureResult(String errMsg);
}

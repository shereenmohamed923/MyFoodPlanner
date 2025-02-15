package com.example.myfoodplanner.network.category;

import com.example.myfoodplanner.model.Category;

import java.util.List;

public interface CategoryNetworkCallBack {
    void onSuccessfulResult(List<Category> categories);
    void onFailureResult(String errMsg);
}

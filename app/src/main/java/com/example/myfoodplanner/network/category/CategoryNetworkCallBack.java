package com.example.myfoodplanner.network.category;

import com.example.myfoodplanner.model.category.Category;

import java.util.List;

public interface CategoryNetworkCallBack {
    void onRetrievedCategory(List<Category> categories);
    void onFailureResult(String errMsg);
}

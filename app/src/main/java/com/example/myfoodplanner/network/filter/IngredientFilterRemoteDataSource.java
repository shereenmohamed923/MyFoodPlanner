package com.example.myfoodplanner.network.filter;

public interface IngredientFilterRemoteDataSource {
    void ingredientMakeNetworkCall(FilterNetworkCallBack filterNetworkCallBack, String ingredient);
}

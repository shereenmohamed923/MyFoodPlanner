package com.example.myfoodplanner.network.ingredient;

import com.example.myfoodplanner.model.ingredient.Ingredient;

import java.util.List;

public interface IngredientNetworkCallBack {
    void onRetrievedIngredients(List<Ingredient> ingredients);
    void onFailureResult(String errMsg);
}

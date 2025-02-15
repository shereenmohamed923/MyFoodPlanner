package com.example.myfoodplanner.network.ingredient;

import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;

public interface IngredientsRemoteDataSource {
    void makeNetworkCall(IngredientNetworkCallBack ingredientNetworkCallBack);
}

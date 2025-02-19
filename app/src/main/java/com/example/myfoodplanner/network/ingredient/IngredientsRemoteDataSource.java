package com.example.myfoodplanner.network.ingredient;

import com.example.myfoodplanner.model.ingredient.IngredientResponse;

import io.reactivex.rxjava3.core.Observable;

public interface IngredientsRemoteDataSource {
//    void makeNetworkCall(IngredientNetworkCallBack ingredientNetworkCallBack);
    Observable<IngredientResponse> getIngredients();
}

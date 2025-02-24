package com.example.myfoodplanner.favourites.view;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface FavouritesView {
    void showFavourites(List<MealDetails> mealDetailsList);
    void showErrMsg(String msg);
}

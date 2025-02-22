package com.example.myfoodplanner.favourites.view;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface FavouritesView {
    public void showFavourites(List<MealDetails> mealDetailsList);
    public void showErrMsg(String msg);
}

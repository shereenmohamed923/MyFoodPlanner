package com.example.myfoodplanner.favourites.presenter;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavouritesPresenter {
    void removeMealFromFavourites(String mealId);
    void getAllFavouriteMeals();
}

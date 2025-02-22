package com.example.myfoodplanner.favourites.presenter;

import android.annotation.SuppressLint;

import com.example.myfoodplanner.favourites.view.FavouritesView;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritesPresenterImpl implements FavouritesPresenter{
    Repository repo;
    FavouritesView view;

    public FavouritesPresenterImpl(Repository repo, FavouritesView view) {
        this.repo = repo;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getFavMeals() {
        repo.getFavouriteMealDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> view.showFavourites(item));
    }

    @Override
    public void removeFromFav(MealDetails mealDetails) {
        repo.deleteFavouriteMealDetails(mealDetails).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}

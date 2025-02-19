package com.example.myfoodplanner.meals.presenter;

import com.example.myfoodplanner.home.view.HomeView;
import com.example.myfoodplanner.meals.view.MealView;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.network.filter.FilterNetworkCallBack;

import java.util.List;

public class MealPresenterImpl implements MealPresenter, FilterNetworkCallBack {
    private MealView view;
    private Repository repo;
    public MealPresenterImpl(MealView view, Repository repo){
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMealsByCategory(String category) {
        repo.getMealsByCategory(this, category);
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        repo.getMealsByIngredient(this, ingredient);
    }

    @Override
    public void getMealsByArea(String area) {
        repo.getMealsByArea(this, area);
    }
    @Override
    public void onRetrievedFilter(List<Meal> meals) {
        view.showFilteredList(meals);
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showErrorMsg(errMsg);
    }
}

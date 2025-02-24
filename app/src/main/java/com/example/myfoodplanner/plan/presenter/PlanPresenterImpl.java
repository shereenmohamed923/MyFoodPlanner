package com.example.myfoodplanner.plan.presenter;

import android.annotation.SuppressLint;

import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.plan.view.PlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter{
    Repository repo;
    PlanView view;

    public PlanPresenterImpl(Repository repo, PlanView view) {
        this.repo = repo;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPlannedMeals() {
        repo.getAllPlannedMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> view.showPlannedMeals(item));
    }

}

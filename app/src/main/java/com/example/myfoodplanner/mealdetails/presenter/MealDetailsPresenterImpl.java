package com.example.myfoodplanner.mealdetails.presenter;

import com.example.myfoodplanner.mealdetails.view.MealDetailsView;
import com.example.myfoodplanner.meals.view.MealView;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    private MealDetailsView view;
    private Repository repo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MealDetailsPresenterImpl(MealDetailsView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMealById(String id) {
        compositeDisposable.add(repo.getMealById(id)
                .subscribeOn(Schedulers.io())
                .map(MealDetailsResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<MealDetails>>() {
                    @Override
                    public void onNext(@NonNull List<MealDetails> mealDetails) {
                        view.showMealDetails(mealDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}

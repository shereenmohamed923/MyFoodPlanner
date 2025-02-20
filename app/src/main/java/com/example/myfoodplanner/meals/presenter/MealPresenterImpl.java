package com.example.myfoodplanner.meals.presenter;

import com.example.myfoodplanner.meals.view.MealView;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.model.filter.MealResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenterImpl implements MealPresenter {
    private MealView view;
    private Repository repo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MealPresenterImpl(MealView view, Repository repo){
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getMealsByCategory(String category) {
        compositeDisposable.add(repo.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .map(MealResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Meal>>() {
                    @Override
                    public void onNext(@NonNull List<Meal> meals) {
                        view.showFilteredList(meals);
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

    @Override
    public void getMealsByIngredient(String ingredient) {
        compositeDisposable.add(repo.getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .map(MealResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Meal>>() {
                    @Override
                    public void onNext(@NonNull List<Meal> meals) {
                        view.showFilteredList(meals);
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

    @Override
    public void getMealsByArea(String area) {
        compositeDisposable.add(repo.getMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .map(MealResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Meal>>() {
                    @Override
                    public void onNext(@NonNull List<Meal> meals) {
                        view.showFilteredList(meals);
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

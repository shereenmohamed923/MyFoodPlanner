package com.example.myfoodplanner.mealdetails.presenter;

import android.util.Log;

import com.example.myfoodplanner.mealdetails.view.MealDetailsView;
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
                        Log.d("sh", "onNext: ");
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

    @Override
    public void AddToFav(MealDetails mealDetails) {
        repo.insertFavouriteMealDetails(mealDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void checkIfMealIsFavourite(String mealId) {
        compositeDisposable.add(repo.isMealFavourite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isFav -> view.updateFavouriteIcon(isFav),
                        throwable -> Log.i("isFav", "checkIfMealIsFavourite: " + throwable)
                ));
    }

    @Override
    public void checkIfMealIsPlanned(String mealId) {
        compositeDisposable.add(repo.isMealPlanned(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isPlanned -> view.updatePlanIcon(isPlanned),
                        throwable -> Log.i("isPlanned", "checkIfMealIsPlanned: " + throwable)
                ));
    }

    @Override
    public void AddToPlan(MealDetails mealDetails) {
        repo.insertMealToPlan(mealDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void removeFromFav(String id) {
        repo.deleteFavouriteMealDetails(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void removeFromPlan(String id) {
        repo.deleteMealFromPlan(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}

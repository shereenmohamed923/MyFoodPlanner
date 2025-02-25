package com.example.myfoodplanner.profile.presenter;

import com.example.myfoodplanner.FireBase.Backup.BackupCallBack;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.profile.view.ProfileView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter, BackupCallBack{
    private ProfileView view;
    private Repository repo;
    List<MealDetails> mealsList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ProfilePresenterImpl(ProfileView view, Repository repo) {
        this.view = view;
        this.repo = repo;
        loadAllMeals();
    }
    private void loadAllMeals() {
        compositeDisposable.add( repo.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> mealsList = list,
                        throwable -> view.showMessage("Error loading meals: " + throwable.getMessage())
                )
        );
    }
    @Override
    public void addToFireStore() {
        if(!mealsList.isEmpty()){
            repo.addMealToFireStore(mealsList);
            view.showMessage("All meals backed up to FireStore!");
        }else {
            view.showMessage("No meals to back up.");
        }
    }

    @Override
    public void logout() {
        repo.logout();
        view.showMessage("logged out successfully");
    }

    @Override
    public void restoreFromFireStore() {
        repo.restoreMealsFromFireStore(this);
        view.showMessage("your data restored successfully");
    }

    @Override
    public void onSuccessfulResult(List<MealDetails> meals) {
        for (int i = 0; i < meals.size(); i++) {
            repo.insertMeal(meals.get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
        view.successfulRestore(meals);
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showMessage("An error occurred "+errMsg);
    }
}

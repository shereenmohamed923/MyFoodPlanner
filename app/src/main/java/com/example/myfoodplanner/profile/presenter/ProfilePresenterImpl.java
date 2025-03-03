package com.example.myfoodplanner.profile.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplanner.FireBase.Backup.AddCallBack;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.profile.view.ProfileView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter, AddCallBack {
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
        compositeDisposable.add(repo.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> mealsList = list,
                        throwable -> view.showMessage("Error loading meals: " + throwable.getMessage())
                )
        );
    }

    @SuppressLint("CheckResult")
    @Override
    public void ClearDataBase() {
        repo.ClearDataBase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showClearDataMessage("Database deleted successfully"),
                        throwable -> view.showMessage( "Error deleting rows")
                );
    }

    @Override
    public void addToFireStore() {
        repo.addMealToFireStore(mealsList, this);
    }

    @Override
    public void signOut() {
        repo.signOut();
    }

    @Override
    public void onSuccess() {
        ClearDataBase();
    }

    @Override
    public void onDeleteSuccess() {
        addToFireStore();
        Log.i("firestore", "onDeleteSuccess: data on fireStore deleted successfully");
    }

}

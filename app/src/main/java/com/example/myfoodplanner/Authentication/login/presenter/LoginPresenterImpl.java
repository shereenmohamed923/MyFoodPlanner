package com.example.myfoodplanner.Authentication.login.presenter;

import android.content.Context;

import com.example.myfoodplanner.Authentication.login.view.LoginView;
import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.FireBase.Backup.BackupCallBack;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter, AuthCallback, BackupCallBack {
    Repository repo;
    LoginView view;
    public LoginPresenterImpl(LoginView view, Repository repo){
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void login(String email, String password, Context context) {
        repo.login(email, password, this, context);
    }

    @Override
    public void googleLogin() {
        view.navigateToHome();
    }

    @Override
    public void restoreFromFireStore() {
        repo.restoreMealsFromFireStore(this);
        view.showMessage("your data restored successfully");
    }
    @Override
    public void onSuccessfulResult(FirebaseUser user) {
        view.navigateToHome();
    }

    @Override
    public void onSuccessfulResult(List<MealDetails> meals) {
        for (int i = 0; i < meals.size(); i++) {
            repo.insertMeal(meals.get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }


    @Override
    public void onFailureResult(String errMsg) {
        view.showMessage(errMsg);
    }
}

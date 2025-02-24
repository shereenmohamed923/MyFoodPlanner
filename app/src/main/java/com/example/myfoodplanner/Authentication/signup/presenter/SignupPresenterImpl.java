package com.example.myfoodplanner.Authentication.signup.presenter;

import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.Authentication.signup.view.SignupView;
import com.example.myfoodplanner.model.Repository;
import com.google.firebase.auth.FirebaseUser;

public class SignupPresenterImpl implements SignupPresenter, AuthCallback {
    Repository repo;
    SignupView view;
    public SignupPresenterImpl(SignupView view, Repository repo){
        this.view = view;
        this.repo = repo;
    }
    @Override
    public void signup(String email, String password) {
        repo.signup(email, password, this);
    }
    @Override
    public void onSuccessfulResult(FirebaseUser user) {
        view.navigateToLogin();
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showMessage(errMsg);
    }
}

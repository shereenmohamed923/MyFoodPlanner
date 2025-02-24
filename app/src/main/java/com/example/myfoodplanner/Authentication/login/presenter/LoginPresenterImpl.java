package com.example.myfoodplanner.Authentication.login.presenter;

import com.example.myfoodplanner.Authentication.login.view.LoginView;
import com.example.myfoodplanner.FireBase.Authentication.AuthCallback;
import com.example.myfoodplanner.model.Repository;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginPresenter, AuthCallback {
    Repository repo;
    LoginView view;
    public LoginPresenterImpl(LoginView view, Repository repo){
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void login(String email, String password) {
        repo.login(email, password, this);
    }
    @Override
    public void onSuccessfulResult(FirebaseUser user) {
        view.navigateToHome();
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showMessage(errMsg);
    }
}

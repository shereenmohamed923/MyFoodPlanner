package com.example.myfoodplanner.Authentication.login.presenter;

import android.content.Context;

public interface LoginPresenter {
    void login(String email, String password, Context context);
    void googleLogin();
    void restoreFromFireStore();
}

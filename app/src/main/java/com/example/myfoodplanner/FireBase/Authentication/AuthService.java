package com.example.myfoodplanner.FireBase.Authentication;

import android.content.Context;

public interface AuthService {
    boolean userExists();
    void signup(String email, String password, AuthCallback authCallback);
    void login(String email, String password, AuthCallback authCallback, Context context);
}

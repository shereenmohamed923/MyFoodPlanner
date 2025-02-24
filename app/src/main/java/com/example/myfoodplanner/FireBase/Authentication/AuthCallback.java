package com.example.myfoodplanner.FireBase.Authentication;

import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {
    void onSuccessfulResult(FirebaseUser user);
    void onFailureResult(String errMsg);
}

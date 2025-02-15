package com.example.myfoodplanner.Authentication.network;

import com.google.firebase.auth.FirebaseUser;

public interface FirebaseCallback {
    void onSuccessfulResult(FirebaseUser user);
    void onFailureResult(String errMsg);
}

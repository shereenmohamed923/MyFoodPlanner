package com.example.myfoodplanner.FireBase.Authentication;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthServiceImpl implements AuthService {
    private static final String TAG = "AuthServiceImpl";
    private static AuthServiceImpl auth = null;
    FirebaseAuth mAuth;
    private AuthServiceImpl() {
        mAuth = FirebaseAuth.getInstance();
    }
    public static AuthServiceImpl getInstance(){
        if(auth == null){
            auth = new AuthServiceImpl();
        }
        return auth;
    }
    @Override
    public Boolean userExists() {
        return mAuth.getCurrentUser() != null;
    }
    @Override
    public void signup(String email, String password, AuthCallback authCallback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "login with email: success ");
                            authCallback.onSuccessfulResult(mAuth.getCurrentUser());
                        } else {
                            Log.i(TAG, "login with email: failure " + task.getException());
                            authCallback.onFailureResult(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password, AuthCallback authCallback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "signup with email: success ");
                            authCallback.onSuccessfulResult(mAuth.getCurrentUser());
                        } else {
                            Log.i(TAG, "signup with email: failure " + task.getException());
                            authCallback.onFailureResult(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void logout() {
        mAuth.signOut();
        Log.i(TAG, "logout: user logged out");
    }
}

package com.example.myfoodplanner.FireBase.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthServiceImpl implements AuthService {
    private static final String TAG = "AuthServiceImpl";
    private static AuthServiceImpl auth = null;
    FirebaseAuth mAuth;

    private AuthServiceImpl() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthServiceImpl getInstance() {
        if (auth == null) {
            auth = new AuthServiceImpl();
        }
        return auth;
    }

    private void saveUserData(String userId, String userEmail, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("USER_ID", userId);
        editor.putString("USER_EMAIL", userEmail);
        editor.putBoolean("IS_LOGGED_IN", true);

        editor.apply();
    }

    @Override
    public boolean userExists() {
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
    public void login(String email, String password, AuthCallback authCallback, Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "signup with email: success ");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                saveUserData(user.getUid(), user.getEmail(), context);
                                authCallback.onSuccessfulResult(user);
                            } else {
                                Log.i(TAG, "signup with email: failure " + task.getException());
                                authCallback.onFailureResult(String.valueOf(task.getException()));
                            }
                        }
                    }
                });
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }
}

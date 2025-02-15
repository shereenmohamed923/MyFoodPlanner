package com.example.myfoodplanner.Authentication.network;

public interface AuthService {
    Boolean userExists();
    void signup(String email, String password, FirebaseCallback firebaseCallback);
    void login(String email, String password, FirebaseCallback firebaseCallback);
    void logout();
}

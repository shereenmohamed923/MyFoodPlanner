package com.example.myfoodplanner.FireBase.Authentication;

public interface AuthService {
    Boolean userExists();
    void signup(String email, String password, AuthCallback authCallback);
    void login(String email, String password, AuthCallback authCallback);
    void logout();
}

package com.example.myfoodplanner.FireBase.Backup;

import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface BackupCallBack {
    void onSuccessfulResult(List<MealDetails> meals);
    void onFailureResult(String errMsg);
}

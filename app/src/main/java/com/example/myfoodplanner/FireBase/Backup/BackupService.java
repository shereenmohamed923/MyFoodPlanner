package com.example.myfoodplanner.FireBase.Backup;

import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface BackupService {
    void addMealToFireStore(List<MealDetails> meals,AddCallBack addCallBack);
    void restoreMealsFromFireStore(BackupCallBack callback);
    void signOut();
}

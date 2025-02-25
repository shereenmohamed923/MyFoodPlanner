package com.example.myfoodplanner.profile.presenter;

import com.example.myfoodplanner.FireBase.Backup.BackupCallBack;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface ProfilePresenter {
    void addToFireStore();
    void logout();
    void restoreFromFireStore();
}

package com.example.myfoodplanner.FireBase.Backup;

import android.util.Log;

import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BackupServiceImpl implements BackupService {
    FirebaseFirestore firestore2;
    FirebaseAuth auth2;
    private static BackupServiceImpl firebase = null;

    public static BackupServiceImpl getInstance() {
        if (firebase == null) {
            firebase = new BackupServiceImpl();
        }
        return firebase;
    }
    @Override
    public void signOut(){
        auth2.signOut();
        Log.i("signout", "logout: user logged out");
    }

    private BackupServiceImpl() {
        auth2 = FirebaseAuth.getInstance();
        firestore2 = FirebaseFirestore.getInstance();
    }

    public String getUserId() {
        FirebaseUser user = auth2.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    @Override
    public void addMealToFireStore(List<MealDetails> meals,AddCallBack addCallBack) {
        if (getUserId() != null) {
            for (MealDetails meal : meals) {
                firestore2.collection("users")
                        .document(getUserId())
                        .collection("meals")
                        .document(meal.getIdMeal())
                        .set(meal)
                        .addOnSuccessListener(aVoid ->addCallBack.onSuccess())
                        .addOnFailureListener(e -> Log.e("Firestore", "Error adding meal: " + meal.getStrMeal(), e));
            }
        } else {
            Log.e("Firestore", "User not logged in, can't add meals");
        }

    }

    @Override
    public void restoreMealsFromFireStore(BackupCallBack callback) {
        firestore2.collection("users")
                .document(getUserId())
                .collection("meals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<MealDetails> meals = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        MealDetails meal = doc.toObject(MealDetails.class);
                        if (meal != null) {
                            meals.add(meal);
                            Log.i("TAG", "Meal fetched: " + meal.getStrMeal());
                        }
                    }
                    callback.onSuccessfulResult(meals);
                })
                .addOnFailureListener(e -> {
                    callback.onFailureResult("Error fetching meals: " + e.getMessage());
                });
    }
}

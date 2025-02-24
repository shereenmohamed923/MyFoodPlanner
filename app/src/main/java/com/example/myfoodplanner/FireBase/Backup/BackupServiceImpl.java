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
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private static BackupServiceImpl firebase = null;

    public static BackupServiceImpl getInstance() {
        if (firebase == null) {
            firebase = new BackupServiceImpl();
        }
        return firebase;
    }

    private BackupServiceImpl() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public String getUserId() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    @Override
    public void addMealToFireStore(MealDetails meal) {
        if (getUserId() != null) {
            firestore.collection("users")
                    .document(getUserId())
                    .collection("meals")
                    .document(String.valueOf(meal.getId()))
                    .set(meal)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal added successfully"))
                    .addOnFailureListener(e -> Log.e("Firestore", "Error adding meal", e));
        } else {
            Log.e("Firestore", "User not logged in, can't add meal");
        }
    }

    @Override
    public void deleteMealFromFireStore(String mealId) {
        if (getUserId() != null) {
            firestore.collection("users")
                    .document(getUserId())
                    .collection("meals")
                    .document(mealId)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal deleted successfully"))
                    .addOnFailureListener(e -> Log.e("Firestore", "Error deleting meal", e));
        } else {
            Log.e("Firestore", "User not logged in, cannot delete meal");
        }
    }

    @Override
    public void restoreMealsFromFireStore(BackupCallBack callback) {
        firestore.collection("users")
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

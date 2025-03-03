package com.example.myfoodplanner;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.network.area.AreaRemoteDataSourceImpl;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSourceImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    Repository repo;
    void setUpPresenter(){
        repo = RepositoryImpl.getInstance(
                CategoriesRemoteDataSourceImpl.getInstance(),
                AuthServiceImpl.getInstance(),
                IngredientsRemoteDataSourceImpl.getInstance(),
                AreaRemoteDataSourceImpl.getInstance(),
                RandomMealRemoteDataSourceImpl.getInstance(),
                CategoryFilterRemoteDataSourceImpl.getInstance(),
                IngredientFilterRemoteDataSourceImpl.getInstance(),
                AreaFilterRemoteDataSourceImpl.getInstance(),
                MealDetailsRemoteDataSourceImpl.getInstance(),
                MealDetailsLocalDataSourceImpl.getInstance(this),
                BackupServiceImpl.getInstance()
        );
    }

    private AlertDialog getAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("save your favourite recipes and plan your week meals!");
        builder.setTitle("Sign Up for More Features");
        builder.setCancelable(false);
        builder.setPositiveButton("Sign Up", (dialog, which) -> {
            navController.navigate(R.id.welcomeFragment);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setUpPresenter();
        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setItemActiveIndicatorColor(ColorStateList.valueOf(Color.parseColor("#679278")));
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        navController = navHostFragment.getNavController();
        NavHostFragment navHostFr = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        if (navHostFr != null) {
            navController = navHostFr.getNavController();
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment ||
                    destination.getId() == R.id.searchFragment ||
                    destination.getId() == R.id.favouritesFragment ||
                    destination.getId() == R.id.planFragment ||
                    destination.getId() == R.id.profileFragment) {
                bottomNavigationView.setVisibility(View.VISIBLE);

            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment || itemId == R.id.searchFragment) {
                navController.navigate(itemId);
                return true;
            }
            boolean exists = repo.userExists();
            if (!exists) {
                AlertDialog alertDialog = getAlertDialog();
                alertDialog.show();
                return false;
            }
            navController.navigate(itemId);
            return true;
        });
    }
}
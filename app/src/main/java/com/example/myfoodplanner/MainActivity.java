package com.example.myfoodplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_bar);
        NavHostFragment navHostFr = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        if (navHostFr!= null) {
            navController = navHostFr.getNavController();
        }
        //NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}
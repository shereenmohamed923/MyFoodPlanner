package com.example.myfoodplanner.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.network.area.AreaRemoteDataSourceImpl;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSourceImpl;
import com.example.myfoodplanner.profile.presenter.ProfilePresenter;
import com.example.myfoodplanner.profile.presenter.ProfilePresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements ProfileView {
    Button logoutBtn;
    Button showFav;
    Button showPlan;
    FirebaseAuth mAuth;
    ProfilePresenter presenter;
    List<MealDetails> meals = new ArrayList<>();
    View view;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        logoutBtn = view.findViewById(R.id.btn_logout);
        showFav = view.findViewById(R.id.btn_show_fav);
        showPlan = view.findViewById(R.id.btn_show_plan);
        setupPresenter();
        mAuth = FirebaseAuth.getInstance();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = logoutDialog();
                alertDialog.show();
            }
        });
        showFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_favouritesFragment);
            }
        });
        showPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_planFragment);
            }
        });
    }
    public void setupPresenter() {
        Repository repository = RepositoryImpl.getInstance(
                CategoriesRemoteDataSourceImpl.getInstance(),
                AuthServiceImpl.getInstance(),
                IngredientsRemoteDataSourceImpl.getInstance(),
                AreaRemoteDataSourceImpl.getInstance(),
                RandomMealRemoteDataSourceImpl.getInstance(),
                CategoryFilterRemoteDataSourceImpl.getInstance(),
                IngredientFilterRemoteDataSourceImpl.getInstance(),
                AreaFilterRemoteDataSourceImpl.getInstance(),
                MealDetailsRemoteDataSourceImpl.getInstance(),
                MealDetailsLocalDataSourceImpl.getInstance(getContext()),
                BackupServiceImpl.getInstance()
        );
        presenter = new ProfilePresenterImpl(this, repository);
    }

    @Override
    public void showMessage(String msg) {
        if (isAdded() && getView() != null) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
            navController.navigate(R.id.action_profileFragment_to_welcomeFragment);
        }
    }

    @Override
    public void showClearDataMessage(String msg) {
        presenter.signOut();
        if (isAdded() && getView() != null) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
            navController.navigate(R.id.action_profileFragment_to_welcomeFragment);
        }
    }
    private AlertDialog logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to Logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("Logout", (dialog, which) -> {
            presenter.addToFireStore();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }
}
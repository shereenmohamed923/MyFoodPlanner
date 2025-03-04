package com.example.myfoodplanner.Authentication.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfoodplanner.Authentication.login.presenter.LoginPresenter;
import com.example.myfoodplanner.Authentication.login.presenter.LoginPresenterImpl;
import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupService;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginFragment extends Fragment implements LoginView {
    private static final String TAG = "LoginFragment";
    EditText email;
    EditText password;
    Button loginBtn;
    FirebaseAuth mAuth;
    LoginPresenter presenter;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
        setupPresenter();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput, passwordInput;
                emailInput = email.getText().toString();
                passwordInput = password.getText().toString();

                if (TextUtils.isEmpty(emailInput)) {
                    email.setError("Enter Email");
                    email.requestFocus(); // Moves focus to the email field
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    password.setError("Enter Password");
                    password.requestFocus(); // Moves focus to the password field
                    return;
                }
                presenter.login(emailInput, passwordInput, getContext());

            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // If focus is lost
                    String emailInput = email.getText().toString().trim();
                    if (TextUtils.isEmpty(emailInput)) {
                        email.setError("Enter Email");
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // If focus is lost
                    String passwordInput = password.getText().toString().trim();
                    if (TextUtils.isEmpty(passwordInput)) {
                        password.setError("Enter Password");
                    }
                }
            }
        });
    }
    private void initializeUI(View view){
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.et_email_login);
        password = view.findViewById(R.id.passwordLogin);
        loginBtn = view.findViewById(R.id.btn_login);
    }
    public void setupPresenter(){
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
        presenter = new LoginPresenterImpl(this, repository);
    }

    @Override
    public void navigateToHome() {
        Log.i(TAG, "signInWithEmail:success");
        Toast.makeText(getContext(), "Welcome to YumDay",
                Toast.LENGTH_SHORT).show();
        FirebaseUser user = mAuth.getCurrentUser();
        presenter.restoreFromFireStore();
        Navigation.findNavController(email).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void successfulRestore(List<MealDetails> meals) {
        Log.d(TAG, "successfulRestore: Your meals are restored successfully");
    }

    @Override
    public void showMessage(String msg) {
        Log.d(TAG, "showMessage: "+msg);
    }
}
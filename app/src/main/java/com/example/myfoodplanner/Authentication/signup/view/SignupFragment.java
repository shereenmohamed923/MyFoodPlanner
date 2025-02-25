package com.example.myfoodplanner.Authentication.signup.view;

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

import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.Authentication.signup.presenter.SignupPresenter;
import com.example.myfoodplanner.Authentication.signup.presenter.SignupPresenterImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
import com.example.myfoodplanner.R;
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
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment implements SignupView {
    private static final String TAG = "SignUpFragment";
    EditText name;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button signupBtn;
    FirebaseAuth mAuth;
    SignupPresenter presenter;

    public SignupFragment() {
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
        setupPresenter();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameInput, emailInput, passwordInput, confirmPasswordInput;
                nameInput = name.getText().toString();
                emailInput = email.getText().toString();
                passwordInput = password.getText().toString();
                confirmPasswordInput = confirmPassword.getText().toString();

                if(TextUtils.isEmpty(nameInput)){
                    Toast.makeText(getContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(emailInput)){
                    Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordInput)){
                    Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPasswordInput)){
                    Toast.makeText(getContext(), "Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passwordInput.equals(confirmPasswordInput)){
                    Toast.makeText(getContext(), "Your passwords didn't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.signup(emailInput, passwordInput);
            }
        });


    }
    public void initializeUI(View view){
        mAuth = FirebaseAuth.getInstance();
        name = view.findViewById(R.id.et_name);
        email = view.findViewById(R.id.et_email);
        password = view.findViewById(R.id.signupPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signupBtn = view.findViewById(R.id.btn_signup);
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
        presenter = new SignupPresenterImpl(this, repository);
    }

    @Override
    public void navigateToLogin() {
        Log.i(TAG, "createUserWithEmail:success");
        Toast.makeText(getContext(), "Authentication success.",
                Toast.LENGTH_SHORT).show();
        Navigation.findNavController(email).navigate(R.id.action_signupFragment_to_loginFragment);
    }
    @Override
    public void showMessage(String msg) {
        Log.i(TAG, "createUserWithEmail:failure");
        Toast.makeText(getContext(), msg,
                Toast.LENGTH_SHORT).show();
    }
}
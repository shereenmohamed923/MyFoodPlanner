package com.example.myfoodplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myfoodplanner.Authentication.login.presenter.LoginPresenter;
import com.example.myfoodplanner.Authentication.login.presenter.LoginPresenterImpl;
import com.example.myfoodplanner.Authentication.login.view.LoginView;
import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

public class WelcomeFragment extends Fragment implements LoginView {
    private static final String TAG = "welcomeFragment";
    private static final int RC_SIGN_IN = 9001;
    Button login;
    Button register;
    Button continueUsingGoogle;
    Button continueAsGuest;
    FirebaseAuth mAuth;
    LoginPresenter presenter;
    GoogleSignInClient mGoogleSignInClient;

    public WelcomeFragment() {
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
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPresenter();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        login = view.findViewById(R.id.btn_welcome_login);
        register = view.findViewById(R.id.btn_welcome_register);
        continueUsingGoogle = view.findViewById(R.id.btn_continue_google);
        continueAsGuest = view.findViewById(R.id.btn_continue_guest);

        // Create Google Sign-In Request
        createRequest();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_signupFragment);
            }
        });

        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_homeFragment);
            }
        });

        continueUsingGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
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
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to Home
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeFragment_to_homeFragment);
        }
    }

    //start signIn intent
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //handle result of signIn intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign-In failed
                Log.i(TAG, "Google sign in failed", e);
                Toast.makeText(requireContext(), "Google Sign-In failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Authenticate with Firebase using the Google account's ID token
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, navigate to home fragment
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            presenter.restoreFromFireStore();
                            // Ensure the view is not null before navigating
                            View view = getView();
                            if (view != null) {
                                Navigation.findNavController(view)
                                        .navigate(R.id.action_welcomeFragment_to_homeFragment);
                            }
                        } else {
                            // If sign-in fails, log the error and show a message
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(requireContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    @Override
    public void navigateToHome() {
        Log.i(TAG, "signInWithEmail:success");
        Toast.makeText(getContext(), "login success.",
                Toast.LENGTH_SHORT).show();
        FirebaseUser user = mAuth.getCurrentUser();
        presenter.restoreFromFireStore();
        Navigation.findNavController(requireView()).navigate(R.id.action_welcomeFragment_to_homeFragment);
    }

    @Override
    public void successfulRestore(List<MealDetails> meals) {
        Log.d(TAG, "successfulRestore: Your meals are restored successfully");
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg,
                Toast.LENGTH_SHORT).show();
    }
}

package com.example.myfoodplanner;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    EditText email;
    EditText password;
    Button loginBtn;
    FirebaseAuth mAuth;

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
        email = view.findViewById(R.id.et_email_login);
        password = view.findViewById(R.id.et_password_login);
        loginBtn = view.findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput, passwordInput;
                emailInput = email.getText().toString();
                passwordInput = password.getText().toString();

                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i(TAG, "signInWithEmail:success");
                                    Toast.makeText(getContext(), "login success.",
                                            Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment2);
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i(TAG, "signInWithEmail:failure");
                                    Toast.makeText(getContext(), "login failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });
    }
}
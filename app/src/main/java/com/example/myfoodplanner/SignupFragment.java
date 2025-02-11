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

public class SignupFragment extends Fragment {
    private static final String TAG = "SignUpFragment";
    EditText name;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button signupBtn;
    FirebaseAuth mAuth;

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

        name = view.findViewById(R.id.et_name);
        email = view.findViewById(R.id.et_email);
        password = view.findViewById(R.id.et_password);
        confirmPassword = view.findViewById(R.id.et_confirm_password);
        signupBtn = view.findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

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
                mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i(TAG, "createUserWithEmail:success");
                                    Toast.makeText(getContext(), "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_homeFragment);
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i(TAG, "createUserWithEmail:failure");
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });


    }
}
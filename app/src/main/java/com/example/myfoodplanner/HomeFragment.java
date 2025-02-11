package com.example.myfoodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    Button logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutBtn = view.findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        if(user == null){
//            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
        }else {
            //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            //userEmail.setText(user.getEmail());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                requireActivity().finishAffinity();
                //Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_welcomeFragment);
               // getActivity().finish();
//                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

    }
}
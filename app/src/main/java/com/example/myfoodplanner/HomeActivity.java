package com.example.myfoodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myfoodplanner.Registeration.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    TextView userEmail;
    Button logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        userEmail = findViewById(R.id.tv_userEmail);
        logoutBtn = findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            userEmail.setText(user.getEmail());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        fetchProducts();
    }

    private void fetchProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealCategoryService mealCategoryService = retrofit.create(MealCategoryService.class);
        Call<MealCategoryResponse> call = mealCategoryService.getMealsCategories();
        call.enqueue(new Callback<MealCategoryResponse>() {
            @Override
            public void onResponse(Call<MealCategoryResponse> call, Response<MealCategoryResponse> response) {
                if(response.isSuccessful()){
                    MealCategoryResponse result = response.body();
                    int sizeOfData = result.getCategories().size();
                    //productsList = result.getProducts();
                    //comment
                    //Toast.makeText(HomeActivity.this,"I received "+sizeOfData + "meal category", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onSuccess: " + result.getCategories());
                }else{
                    Log.i(TAG, "onFailure: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<MealCategoryResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
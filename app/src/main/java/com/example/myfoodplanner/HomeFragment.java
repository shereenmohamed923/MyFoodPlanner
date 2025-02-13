package com.example.myfoodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    Button logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    RecyclerView recyclerView;
    List<MealCategory> mealCategories;
    Button filterBtn;

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
        filterBtn = view.findViewById(R.id.btn_filter);
        mAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                requireActivity().finishAffinity();
            }
        });

        fetchProducts();
        recyclerView = view.findViewById(R.id.rv_meal_categories);

//        filterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.home);
//            }
//        });

    }

    private void fetchProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealService mealService = retrofit.create(MealService.class);
        Call<MealCategoryResponse> call = mealService.getMealsCategories();
        call.enqueue(new Callback<MealCategoryResponse>() {
            @Override
            public void onResponse(Call<MealCategoryResponse> call, Response<MealCategoryResponse> response) {
                if(response.isSuccessful()){
                    MealCategoryResponse result = response.body();
                    if(result != null){
                        int sizeOfData = result.getCategories().size();
                        mealCategories = result.getCategories();
                        MealCategoriesRecyclerViewAdapter adapter = new MealCategoriesRecyclerViewAdapter(getContext(), mealCategories);
                        recyclerView.setAdapter(adapter);
                        Log.i(TAG, "onSuccess: " + sizeOfData);
                    }

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




//user = mAuth.getCurrentUser();

//        if(user == null){
//            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
//            //userEmail.setText(user.getEmail());
//        }
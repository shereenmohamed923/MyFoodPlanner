package com.example.myfoodplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FilterFragment extends Fragment {
    private static final String TAG = "FilterFragment";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    RecyclerView recyclerView;
    List<MealCategory> mealCategories;
    ChipGroup filter;

    public FilterFragment() {
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
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchProducts();
        recyclerView = view.findViewById(R.id.rv_meal_categories);
        filter.findViewById(R.id.cg_filter);
        filter.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
            }
        });
    }

        private void fetchProducts () {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MealService mealService = retrofit.create(MealService.class);
            Call<MealCategoryResponse> call = mealService.getMealsCategories();
            call.enqueue(new Callback<MealCategoryResponse>() {
                @Override
                public void onResponse(Call<MealCategoryResponse> call, Response<MealCategoryResponse> response) {
                    if (response.isSuccessful()) {
                        MealCategoryResponse result = response.body();
                        if (result != null) {
                            int sizeOfData = result.getCategories().size();
                            mealCategories = result.getCategories();
                            MealCategoriesRecyclerViewAdapter adapter = new MealCategoriesRecyclerViewAdapter(getContext(), mealCategories);
                            recyclerView.setAdapter(adapter);
                            Log.i(TAG, "onSuccess: " + sizeOfData);
                        }

                    } else {
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
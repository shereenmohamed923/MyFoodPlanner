package com.example.myfoodplanner.mealdetails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.mealdetails.presenter.MealDetailsPresenter;
import com.example.myfoodplanner.mealdetails.presenter.MealDetailsPresenterImpl;
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

import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private static final String TAG = "MealDetailsFragment";
    MealDetailsPresenter presenter;
    ImageView mealImg;
    TextView mealTitle;
    TextView mealArea;


    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        initializeUI(view);
        setupPresenter();
        presenter.getMealById(id);
    }
    private void initializeUI(View view){
        mealTitle = view.findViewById(R.id.tv_meal_title);
        mealArea = view.findViewById(R.id.tv_meal_area);
        mealImg = view.findViewById(R.id.iv_meal_thumbnail);
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
                MealDetailsRemoteDataSourceImpl.getInstance()
        );
        presenter = new MealDetailsPresenterImpl(this, repository);
    }

    @Override
    public void showMealDetails(List<MealDetails> mealDetails) {
        Log.i(TAG, "showMealDetails: "+mealDetails.get(0).getStrMeal());
        mealTitle.setText(mealDetails.get(0).getStrMeal());
        mealArea.setText(mealDetails.get(0).getStrArea() + " Cuisine");
        String url = mealDetails.get(0).getStrMealThumb();
        Glide.with(getContext()).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mealImg);
    }

    @Override
    public void showErrorMsg(String msg) {
        Log.i(TAG, "showErrorMsg: "+msg);
    }
}
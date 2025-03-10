package com.example.myfoodplanner.home.view;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.home.presenter.HomePresenter;
import com.example.myfoodplanner.home.presenter.HomePresenterImpl;
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.model.ingredient.Ingredient;
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

import java.util.List;

public class HomeFragment extends Fragment implements OnListClickListener, HomeView {
    private static final String TAG = "HomeFragment";
    FirebaseAuth mAuth;
    TextView tv_meal_name;
    ImageView iv_meal_image;
    TextView tv_meal_area;
    TextView tv_meal_category;
    MealDetails randomMealDetails;
    CardView randomMeal;
    RecyclerView categoriesRecyclerView;
    RecyclerView ingredientsRecyclerView;
    RecyclerView areasRecyclerView;
    CategoriesAdapter categoriesAdapter;
    IngredientsAdapter ingredientsAdapter;
    AreasAdapter areasAdapter;
    View view;
    HomePresenter presenter;

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
        initializeUI(view);
        this.view = view;
        categoriesAdapter = new CategoriesAdapter(view.getContext(), this);
        ingredientsAdapter = new IngredientsAdapter(view.getContext(), this);
        areasAdapter = new AreasAdapter(view.getContext(), this);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        areasRecyclerView.setAdapter(areasAdapter);
        setupPresenter();
        presenter.getCategories();
        presenter.getIngredients();
        presenter.getAreas();
        presenter.getRandomMeal();
        randomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action
                        = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(randomMealDetails.getIdMeal(), new MealDetails());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
    private void initializeUI(View view){
        mAuth = FirebaseAuth.getInstance();
        categoriesRecyclerView = view.findViewById(R.id.rv_meal_categories);
        ingredientsRecyclerView = view.findViewById(R.id.rv_ingredients);
        areasRecyclerView = view.findViewById(R.id.rv_countries);
        tv_meal_name = view.findViewById(R.id.tv_meal_name);
        iv_meal_image = view.findViewById(R.id.iv_details_img);
        randomMeal = view.findViewById(R.id.cv_random_meal);
        tv_meal_area = view.findViewById(R.id.tv_today_meal_area);
        tv_meal_category = view.findViewById(R.id.tv_today_meal_category);
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
        presenter = new HomePresenterImpl(this, repository);
    }

    @Override
    public void onCategoryClick(String category, String type) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action
                = HomeFragmentDirections.actionHomeFragmentToMealsFragment(category, type);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onIngredientClick(String ingredient, String type) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action
                = HomeFragmentDirections.actionHomeFragmentToMealsFragment(ingredient, type);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onAreaClick(String area, String type) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action
                = HomeFragmentDirections.actionHomeFragmentToMealsFragment(area, type);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void showCategoriesList(List<Category> categories) {
        Log.i(TAG, "onSuccess: categories list Received " + categories.size());
        categoriesAdapter.setCategoriesList(categories);
    }

    @Override
    public void showIngredientsList(List<Ingredient> ingredients) {
        Log.i(TAG, "onSuccess: ingredients list Received " + ingredients.size());
        ingredientsAdapter.setIngredientsList(ingredients);
    }

    @Override
    public void showAreasList(List<Area> areas) {
        Log.i(TAG, "onSuccess: areas list Received " + areas.size());
        areasAdapter.setAreasList(areas);
    }

    @Override
    public void showRandomMeal(List<MealDetails> mealDetails) {
        randomMealDetails = mealDetails.get(0);
        Log.i(TAG, "onSuccess: random meal Received " + mealDetails.get(0).getStrMeal());
        String mealName = mealDetails.get(0).getStrMeal();
        String mealImg = mealDetails.get(0).getStrMealThumb();
        String mealArea = mealDetails.get(0).getStrArea();
        String mealCategory = mealDetails.get(0).getStrCategory();
        tv_meal_name.setText(mealName);
        tv_meal_area.setText(mealArea + " Cuisine");
        tv_meal_category.setText(mealCategory);
        Glide.with(getContext()).load(mealImg)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(iv_meal_image);
    }

    @Override
    public void showErrorMsg(String msg) {
        Log.i(TAG, "onFailure: " + msg);
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }
}

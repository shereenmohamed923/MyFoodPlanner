package com.example.myfoodplanner.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.home.presenter.HomePresenterImpl;
import com.example.myfoodplanner.home.view.AreasAdapter;
import com.example.myfoodplanner.home.view.CategoriesAdapter;
import com.example.myfoodplanner.home.view.HomeFragmentDirections;
import com.example.myfoodplanner.home.view.IngredientsAdapter;
import com.example.myfoodplanner.home.view.OnListClickListener;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.network.area.AreaRemoteDataSourceImpl;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSourceImpl;
import com.example.myfoodplanner.search.presenter.SearchPresenter;
import com.example.myfoodplanner.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView, OnListClickListener {
    private static final String TAG = "SearchFragment";
    ChipGroup chipGroup;
    View view;
    RecyclerView searchRecycler;
    CategoriesAdapter categoriesAdapter;
    IngredientsAdapter ingredientsAdapter;
    AreasAdapter areasAdapter;
    SearchPresenter presenter;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
        this.view = view;
        categoriesAdapter = new CategoriesAdapter(view.getContext(), this);
        ingredientsAdapter = new IngredientsAdapter(view.getContext(), this);
        areasAdapter = new AreasAdapter(view.getContext(), this);
        setupPresenter();
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                Chip chip = group.findViewById(checkedId);
                String choice = chip.getText().toString();
                Log.d(TAG, "Selected chip: " + choice);
                switch (choice) {
                    case "Category":
                        searchRecycler.setAdapter(categoriesAdapter);
                        searchRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        presenter.getCategories();
                        break;
                    case "Ingredient":
                        searchRecycler.setAdapter(ingredientsAdapter);
                        searchRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        presenter.getIngredients();
                        break;
                    case "Country":
                        searchRecycler.setAdapter(areasAdapter);
                        searchRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        presenter.getAreas();
                        break;
                }
                }
        });
    }
    private void initializeUI(View view){
        chipGroup = view.findViewById(R.id.chip_group);
        searchRecycler = view.findViewById(R.id.rv_search);
        chipGroup.clearCheck();
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
                MealDetailsRemoteDataSourceImpl.getInstance(),
                MealDetailsLocalDataSourceImpl.getInstance(getContext())
        );
        presenter = new SearchPresenterImpl(this, repository);
    }

    @Override
    public void showCategoriesList(List<Category> categories) {
        Log.i(TAG, "onSuccess: categories list Received " + categories.size());
        categoriesAdapter.setCategoriesList(categories);
        categoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredientsList(List<Ingredient> ingredients) {
        Log.i(TAG, "onSuccess: ingredients list Received " + ingredients.size());
        ingredientsAdapter.setIngredientsList(ingredients);
        ingredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAreasList(List<Area> areas) {
        Log.i(TAG, "onSuccess: areas list Received " + areas.size());
        areasAdapter.setAreasList(areas);
        areasAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String msg) {
        Log.i(TAG, "onFailure: " + msg);
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(String category, String type) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action
                = SearchFragmentDirections.actionSearchFragmentToMealsFragment(category, type);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onIngredientClick(String ingredient, String type) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action
                = SearchFragmentDirections.actionSearchFragmentToMealsFragment(ingredient, type);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onAreaClick(String area, String type) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action
                = SearchFragmentDirections.actionSearchFragmentToMealsFragment(area, type);
        Navigation.findNavController(view).navigate(action);
    }
}
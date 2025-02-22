package com.example.myfoodplanner.meals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.meals.presenter.MealPresenter;
import com.example.myfoodplanner.meals.presenter.MealPresenterImpl;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.model.filter.Meal;
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

public class MealsFragment extends Fragment implements OnMealClickListener, MealView {
    private static final String TAG = "MealsFragment";
    RecyclerView mealsRecycler;
    MealsAdapter mealsAdapter;
    MealPresenter presenter;
    View view;

    public MealsFragment() {
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
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
        this.view = view;
        String filter = MealsFragmentArgs.fromBundle(getArguments()).getFilterType();
        String type = MealsFragmentArgs.fromBundle(getArguments()).getType();
        mealsAdapter = new MealsAdapter(view.getContext(), this);
        mealsRecycler.setAdapter(mealsAdapter);
        setupPresenter();
        if(type.equals("c")){
            presenter.getMealsByCategory(filter);
        }else if(type.equals("i")){
            presenter.getMealsByIngredient(filter);
        }else{
            presenter.getMealsByArea(filter);
        }
    }
    private void initializeUI(View view){
        mealsRecycler = view.findViewById(R.id.rv_meals);
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
        presenter = new MealPresenterImpl(this, repository);
    }
    @Override
    public void showFilteredList(List<Meal> filteredMeals) {
        Log.i(TAG, "onSuccess: list Received " + filteredMeals.get(0).getStrMeal());
        mealsAdapter.setMealsList(filteredMeals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String msg) {
        Log.i(TAG, "onFailure: " + msg);
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(Meal meal) {
        MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment action
                = MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment(meal.getIdMeal(), new MealDetails());
        Navigation.findNavController(view).navigate(action);
    }
}
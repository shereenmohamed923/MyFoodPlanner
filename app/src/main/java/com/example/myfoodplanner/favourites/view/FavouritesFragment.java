package com.example.myfoodplanner.favourites.view;

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

import com.example.myfoodplanner.FireBase.Authentication.AuthServiceImpl;
import com.example.myfoodplanner.FireBase.Backup.BackupServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.favourites.presenter.FavouritesPresenter;
import com.example.myfoodplanner.favourites.presenter.FavouritesPresenterImpl;
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

public class FavouritesFragment extends Fragment implements FavouritesView, OnFavProductClickListener{
    private static final String TAG = "FavouritesFragment";
    RecyclerView favouritesRecycler;
    FavouritesAdapter favouritesAdapter;
    FavouritesPresenter presenter;
    View view;

    public FavouritesFragment() {
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
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        favouritesRecycler = view.findViewById(R.id.rv_favourites);
        favouritesAdapter = new FavouritesAdapter(view.getContext(), this);
        setupPresenter();
        presenter.getAllFavouriteMeals();

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
                MealDetailsLocalDataSourceImpl.getInstance(getContext()),
                BackupServiceImpl.getInstance()
        );
        presenter = new FavouritesPresenterImpl(repository, this);
    }

    @Override
    public void showFavourites(List<MealDetails> mealDetailsList) {
        if(!mealDetailsList.isEmpty()){
            Log.i(TAG, "showFavourites: " +mealDetailsList.get(0).getStrMeal());
            favouritesRecycler.setAdapter(favouritesAdapter);
            favouritesAdapter.setList(mealDetailsList);
        }
    }

    @Override
    public void showErrMsg(String msg) {
        Log.i(TAG, "onFailure: " + msg);
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavMealClick(MealDetails mealDetails) {
        FavouritesFragmentDirections.ActionFavouritesFragmentToMealDetailsFragment action
                = FavouritesFragmentDirections.actionFavouritesFragmentToMealDetailsFragment("", mealDetails);
        Navigation.findNavController(view).navigate(action);
    }
}
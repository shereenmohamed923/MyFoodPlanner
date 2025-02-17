package com.example.myfoodplanner.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
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
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.mealdetails.DetailsRemoteDataSource;
import com.example.myfoodplanner.network.mealdetails.DetailsRemoteDataSourceImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeFragment extends Fragment implements OnMealClickListener, HomeView {
    private static final String TAG = "HomeFragment";
    FirebaseAuth mAuth;
//    FirebaseUser user;
    TextView tv_meal_name;
    ImageView iv_meal_image;
    RecyclerView categoriesRecyclerView;
    RecyclerView ingredientsRecyclerView;
    RecyclerView areasRecyclerView;
    CategoriesAdapter categoriesAdapter;
    IngredientsAdapter ingredientsAdapter;
    AreasAdapter areasAdapter;
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
        presenter.getMealDetails();

//        filterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.home);
//            }
//        });

    }
    private void initializeUI(View view){
        mAuth = FirebaseAuth.getInstance();
        categoriesRecyclerView = view.findViewById(R.id.rv_meal_categories);
        ingredientsRecyclerView = view.findViewById(R.id.rv_ingredients);
        areasRecyclerView = view.findViewById(R.id.rv_countries);
        tv_meal_name = view.findViewById(R.id.tv_meal_name);
        iv_meal_image = view.findViewById(R.id.iv_details_img);
    }
    public void setupPresenter(){
        Repository repository = RepositoryImpl.getInstance(
                CategoriesRemoteDataSourceImpl.getInstance(),
                AuthServiceImpl.getInstance(),
                IngredientsRemoteDataSourceImpl.getInstance(),
                AreaRemoteDataSourceImpl.getInstance(),
                DetailsRemoteDataSourceImpl.getInstance()
                );
        presenter = new HomePresenterImpl(this, repository);
    }

    @Override
    public void onCategoryClick(Category category) {
        //pass the id to get category meals
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        //pass the name to get ingredients meals
    }

    @Override
    public void onAreaClick(Area area) {
        //pass name to get all the meals in that area
    }

    @Override
    public void onMealClick(MealDetails meal) {
        //pass meal id to get meal details or pass object to the next fragment
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
    public void showMealDetails(List<MealDetails> mealDetails) {
        String mealName = mealDetails.get(0).getStrMeal();
        String mealImg = mealDetails.get(0).getStrMealThumb();
        tv_meal_name.setText(mealName);
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




//user = mAuth.getCurrentUser();

//        if(user == null){
//            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
//            //userEmail.setText(user.getEmail());
//        }
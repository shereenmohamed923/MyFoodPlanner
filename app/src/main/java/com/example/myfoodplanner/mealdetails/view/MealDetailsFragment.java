package com.example.myfoodplanner.mealdetails.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.mealdetails.presenter.MealDetailsPresenter;
import com.example.myfoodplanner.mealdetails.presenter.MealDetailsPresenterImpl;
import com.example.myfoodplanner.model.mealdetails.IngredientDetails;
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

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private static final String TAG = "MealDetailsFragment";
    RecyclerView ingredientsRecycler;
    RecyclerView instructionsRecycler;
    IngredientDetailsAdapter ingredientDetailsAdapter;
    RecipeStepsAdapter recipeStepsAdapter;
    MealDetailsPresenter presenter;
    ImageView mealImg;
    TextView mealTitle;
    TextView mealArea;
    WebView youtubeVideo;

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
        ingredientDetailsAdapter = new IngredientDetailsAdapter(view.getContext());
        ingredientsRecycler.setAdapter(ingredientDetailsAdapter);
        recipeStepsAdapter = new RecipeStepsAdapter(view.getContext());
        instructionsRecycler.setAdapter(recipeStepsAdapter);
        setupPresenter();
        presenter.getMealById(id);
    }
    private void initializeUI(View view){
        mealTitle = view.findViewById(R.id.tv_meal_title);
        mealArea = view.findViewById(R.id.tv_meal_area);
        mealImg = view.findViewById(R.id.iv_meal_thumbnail);
        ingredientsRecycler = view.findViewById(R.id.rv_ingredients_list);
        instructionsRecycler = view.findViewById(R.id.rv_instructions);
        youtubeVideo = view.findViewById(R.id.wv_youtube);
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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showMealDetails(List<MealDetails> mealDetails) {
        Log.i(TAG, "showMealDetails: "+mealDetails.get(0).getIngredient(1));
        MealDetails meal = mealDetails.get(0);
        mealTitle.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea() + " Cuisine");
        String url = meal.getStrMealThumb();
        Glide.with(getContext()).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mealImg);
        List<IngredientDetails> ingredientsList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String name = meal.getIngredient(i);
            String measurement = meal.getMeasurement(i);
            String thumbnail = "https://www.themealdb.com/images/ingredients/"+name+"-Small.png";
            if(name != null && !name.isEmpty()){
                ingredientsList.add(new IngredientDetails(name, measurement, url));
            }
        }
        ingredientDetailsAdapter.setIngredientDetails(ingredientsList);
        String instructions = meal.getStrInstructions();
        List<String> stepsList = new ArrayList<>();
        if (instructions != null && !instructions.isEmpty()) {
            String[] stepsArray = instructions.split("\\."); // Splitting by ". " to get steps
            for (String step : stepsArray) {
                stepsList.add(step.trim()); // Trim removes any extra spaces
            }
            recipeStepsAdapter.setRecipeSteps(stepsList);
        }
        String videoId = extractYouTubeVideoId(meal.getStrYoutube());
        String embedHtml = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/" + videoId + "' " +
                "frameborder='0' allowfullscreen></iframe></body></html>";
        youtubeVideo.getSettings().setJavaScriptEnabled(true);
        youtubeVideo.setWebChromeClient(new WebChromeClient());
        youtubeVideo.loadData(embedHtml, "text/html", "utf-8");
    }
    @Override
    public void showErrorMsg(String msg) {
        Log.i(TAG, "showErrorMsg: "+msg);
    }
    private String extractYouTubeVideoId(String url) {
        if (url != null && url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2);
        }
        return "";
    }
}
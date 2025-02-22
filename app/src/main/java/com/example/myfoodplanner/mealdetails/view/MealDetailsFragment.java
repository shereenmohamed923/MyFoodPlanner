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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.favourites.presenter.FavouritesPresenter;
import com.example.myfoodplanner.favourites.presenter.FavouritesPresenterImpl;
import com.example.myfoodplanner.favourites.view.FavouritesView;
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

public class MealDetailsFragment extends Fragment implements MealDetailsView, FavouritesView {
    private static final String TAG = "MealDetailsFragment";
    RecyclerView ingredientsRecycler;
    RecyclerView instructionsRecycler;
    IngredientDetailsAdapter ingredientDetailsAdapter;
    RecipeStepsAdapter recipeStepsAdapter;
    MealDetailsPresenter presenter;
    FavouritesPresenter favPresenter;
    ImageView mealImg;
    TextView mealTitle;
    TextView mealArea;
    WebView youtubeVideo;
    ImageView favouriteBtn;
    String id;
    private boolean isFavourite = false;
    List<MealDetails> mealDetailsList = new ArrayList<>();

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
        id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        initializeUI(view);
        ingredientDetailsAdapter = new IngredientDetailsAdapter(view.getContext());
        ingredientsRecycler.setAdapter(ingredientDetailsAdapter);
        recipeStepsAdapter = new RecipeStepsAdapter(view.getContext());
        instructionsRecycler.setAdapter(recipeStepsAdapter);
        setupPresenter();
        setupFavPresenter();
        if(!id.isEmpty()){
            presenter.checkIfMealIsFavourite(id);
            presenter.getMealById(id);
        }else{
            mealDetailsList.add(MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails());
            presenter.checkIfMealIsFavourite(MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetails().getIdMeal());
            showMealDetails(mealDetailsList);
        }
        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavourite) {
                    favPresenter.removeFromFav(mealDetailsList.get(0));
                    favouriteBtn.setImageResource(R.drawable.heart);
                    isFavourite = false; // Update state
                    Toast.makeText(getContext(), mealDetailsList.get(0).getStrMeal() + " removed from favourites", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: " + mealDetailsList.get(0).getStrMeal() + " removed from favourites");
                } else {
                    presenter.AddToFav(mealDetailsList.get(0));
                    favouriteBtn.setImageResource(R.drawable.heart_fill);
                    isFavourite = true; // Update state
                    Toast.makeText(getContext(), mealDetailsList.get(0).getStrMeal() + " added to favourites", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: " + mealDetailsList.get(0).getStrMeal() + " added to favourites");
                }

            }
        });
    }
    private void initializeUI(View view){
        mealTitle = view.findViewById(R.id.tv_meal_title);
        mealArea = view.findViewById(R.id.tv_meal_area);
        mealImg = view.findViewById(R.id.iv_meal_thumbnail);
        ingredientsRecycler = view.findViewById(R.id.rv_ingredients_list);
        instructionsRecycler = view.findViewById(R.id.rv_instructions);
        youtubeVideo = view.findViewById(R.id.wv_youtube);
        favouriteBtn = view.findViewById(R.id.iv_non_favourite);
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
        presenter = new MealDetailsPresenterImpl(this, repository);
    }
    public void setupFavPresenter() {
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
        favPresenter = new FavouritesPresenterImpl(repository, this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showMealDetails(List<MealDetails> mealDetails) {
        mealDetailsList.add(mealDetails.get(0));
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
            String[] stepsArray = instructions.split("\\.");
            for (String step : stepsArray) {
                stepsList.add(step.trim());
            }
            recipeStepsAdapter.setRecipeSteps(stepsList);
        }
        //youtube video display
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

    @Override
    public void updateFavouriteIcon(boolean isFavourite) {
        this.isFavourite = isFavourite;
        if(isFavourite){
            favouriteBtn.setImageResource(R.drawable.heart_fill);
        }else{
            favouriteBtn.setImageResource(R.drawable.heart);
        }
    }

    private String extractYouTubeVideoId(String url) {
        if (url != null && url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2);
        }
        return "";
    }

    @Override
    public void showFavourites(List<MealDetails> mealDetailsList) {}

    @Override
    public void showErrMsg(String msg) {}
}
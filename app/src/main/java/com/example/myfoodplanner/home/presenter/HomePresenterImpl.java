package com.example.myfoodplanner.home.presenter;

import com.example.myfoodplanner.home.view.HomeView;
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.network.area.AreaNetworkCallBack;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.filter.FilterNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;
import com.example.myfoodplanner.network.mealdetails.DetailsNetworkCallBack;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, CategoryNetworkCallBack, IngredientNetworkCallBack, AreaNetworkCallBack, DetailsNetworkCallBack {
   private HomeView view;
   private Repository repo;
   public HomePresenterImpl(HomeView view, Repository repo){
       this.view = view;
       this.repo = repo;
   }
    @Override
    public void getCategories() {
        repo.getCategories(this);
    }

    @Override
    public void getIngredients() {
        repo.getIngredients(this);
    }

    @Override
    public void getAreas() {
        repo.getAreas(this);
    }

    @Override
    public void getMealDetails() {
        repo.getMealDetails(this);
    }
    @Override
    public void onRetrievedCategory(List<Category> categories) {
        view.showCategoriesList(categories);
    }

    @Override
    public void onRetrievedIngredients(List<Ingredient> ingredients) {
        view.showIngredientsList(ingredients);
    }

    @Override
    public void onRetrievedArea(List<Area> areas) {
        view.showAreasList(areas);
    }

    @Override
    public void onRetrievedDetails(List<MealDetails> mealDetails) {
        view.showMealDetails(mealDetails);
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showErrorMsg(errMsg);
    }
}

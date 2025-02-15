package com.example.myfoodplanner.home.presenter;

import com.example.myfoodplanner.home.view.HomeView;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;
import com.example.myfoodplanner.network.ingredient.IngredientNetworkCallBack;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, CategoryNetworkCallBack, IngredientNetworkCallBack {
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
    public void onRetrievedCategory(List<Category> categories) {
        view.showCategoriesList(categories);
    }

    @Override
    public void onRetrievedIngredients(List<Ingredient> ingredients) {
        view.showIngredientsList(ingredients);
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showErrorMsg(errMsg);
    }
}

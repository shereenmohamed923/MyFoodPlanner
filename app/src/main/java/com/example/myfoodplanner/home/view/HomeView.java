package com.example.myfoodplanner.home.view;

import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.ingredient.Ingredient;

import java.util.List;

public interface HomeView {
    void showCategoriesList(List<Category> categories);
    void showIngredientsList(List<Ingredient> ingredients);
    void showErrorMsg(String msg);
}

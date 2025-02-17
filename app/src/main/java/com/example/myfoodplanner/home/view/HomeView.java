package com.example.myfoodplanner.home.view;

import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.List;

public interface HomeView {
    void showCategoriesList(List<Category> categories);
    void showIngredientsList(List<Ingredient> ingredients);
    void showAreasList(List<Area> areas);
    void showMealDetails(List<MealDetails> mealDetails);
    void showErrorMsg(String msg);
}

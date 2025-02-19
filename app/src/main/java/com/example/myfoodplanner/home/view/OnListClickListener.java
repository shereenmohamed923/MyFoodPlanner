package com.example.myfoodplanner.home.view;

import android.view.View;

import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.filter.Meal;
import com.example.myfoodplanner.model.ingredient.Ingredient;

public interface OnListClickListener {
    void onCategoryClick(String category, String type);
    void onIngredientClick(String ingredient, String type);
    void onAreaClick(String area, String type);
}

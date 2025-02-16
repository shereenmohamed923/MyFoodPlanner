package com.example.myfoodplanner.home.view;

import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.ingredient.Ingredient;

public interface OnMealClickListener {
    void onCategoryClick(Category category);
    void onIngredientClick(Ingredient ingredient);
    void onAreaClick(Area area);
}

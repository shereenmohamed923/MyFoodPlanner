package com.example.myfoodplanner.home.view;

import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.mealdetails.MealDetails;

public interface OnMealClickListener {
    void onCategoryClick(Category category);
    void onIngredientClick(Ingredient ingredient);
    void onAreaClick(Area area);
    void onMealClick(MealDetails meal);
}

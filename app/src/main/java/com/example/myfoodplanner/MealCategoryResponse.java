package com.example.myfoodplanner;

import java.util.List;

public class MealCategoryResponse {
    private List<MealCategory> categories;

    public MealCategoryResponse(List<MealCategory> categories) {
        this.categories = categories;
    }

    public List<MealCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MealCategory> categories) {
        this.categories = categories;
    }
}

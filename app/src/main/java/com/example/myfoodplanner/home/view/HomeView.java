package com.example.myfoodplanner.home.view;

import com.example.myfoodplanner.model.Category;

import java.util.List;

public interface HomeView {
    void showCategoriesList(List<Category> categories);
    void showErrorMsg(String msg);
}

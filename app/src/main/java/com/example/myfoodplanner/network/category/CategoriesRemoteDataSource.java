package com.example.myfoodplanner.network.category;

import com.example.myfoodplanner.model.category.CategoryResponse;

import io.reactivex.rxjava3.core.Observable;

public interface CategoriesRemoteDataSource {
    Observable<CategoryResponse> getCategories();
}

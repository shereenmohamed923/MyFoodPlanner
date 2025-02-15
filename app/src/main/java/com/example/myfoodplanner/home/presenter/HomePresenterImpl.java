package com.example.myfoodplanner.home.presenter;

import com.example.myfoodplanner.home.view.HomeView;
import com.example.myfoodplanner.model.Category;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.network.category.CategoryNetworkCallBack;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, CategoryNetworkCallBack {
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
    public void onSuccessfulResult(List<Category> categories) {
        view.showCategoriesList(categories);
    }

    @Override
    public void onFailureResult(String errMsg) {
        view.showErrorMsg(errMsg);
    }
}

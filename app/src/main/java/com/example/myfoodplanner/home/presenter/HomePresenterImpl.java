package com.example.myfoodplanner.home.presenter;

import com.example.myfoodplanner.home.view.HomeView;
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.area.AreaResponse;
import com.example.myfoodplanner.model.category.Category;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.category.CategoryResponse;
import com.example.myfoodplanner.model.ingredient.Ingredient;
import com.example.myfoodplanner.model.ingredient.IngredientResponse;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.model.mealdetails.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
   private HomeView view;
   private Repository repo;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
   public HomePresenterImpl(HomeView view, Repository repo){
       this.view = view;
       this.repo = repo;
   }
    @Override
    public void getCategories() {
        compositeDisposable.add(repo.getCategories()
                .subscribeOn(Schedulers.io())
                .map(CategoryResponse::getCategories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Category>>(){

                    @Override
                    public void onNext(@NonNull List<Category> categories) {
                        view.showCategoriesList(categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    @Override
    public void getIngredients() {
        compositeDisposable.add(repo.getIngredients()
                .subscribeOn(Schedulers.io())
                .map(IngredientResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Ingredient>>() {
                    @Override
                    public void onNext(@NonNull List<Ingredient> ingredients) {
                        view.showIngredientsList(ingredients);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
    @Override
    public void getAreas() {
        compositeDisposable.add(repo.getAreas()
                .subscribeOn(Schedulers.io())
                .map(AreaResponse::getAreas)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Area>>() {
                    @Override
                    public void onNext(@NonNull List<Area> areas) {
                        view.showAreasList(areas);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
    @Override
    public void getMealDetails() {
        compositeDisposable.add(repo.getMealDetails()
                .subscribeOn(Schedulers.io())
                .map(MealDetailsResponse::getMeals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<MealDetails>>() {
                    @Override
                    public void onNext(@NonNull List<MealDetails> mealDetails) {
                        view.showMealDetails(mealDetails);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}

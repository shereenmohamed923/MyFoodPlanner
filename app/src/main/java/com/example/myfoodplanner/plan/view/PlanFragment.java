package com.example.myfoodplanner.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfoodplanner.Authentication.network.AuthServiceImpl;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.database.MealDetailsLocalDataSourceImpl;
import com.example.myfoodplanner.favourites.presenter.FavouritesPresenterImpl;
import com.example.myfoodplanner.favourites.view.FavouritesFragmentDirections;
import com.example.myfoodplanner.model.Repository;
import com.example.myfoodplanner.model.RepositoryImpl;
import com.example.myfoodplanner.model.mealdetails.MealDetails;
import com.example.myfoodplanner.network.area.AreaRemoteDataSourceImpl;
import com.example.myfoodplanner.network.category.CategoriesRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.AreaFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.CategoryFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.filter.IngredientFilterRemoteDataSourceImpl;
import com.example.myfoodplanner.network.ingredient.IngredientsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.mealdetails.MealDetailsRemoteDataSourceImpl;
import com.example.myfoodplanner.network.randommeal.RandomMealRemoteDataSourceImpl;
import com.example.myfoodplanner.plan.presenter.PlanPresenter;
import com.example.myfoodplanner.plan.presenter.PlanPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlanFragment extends Fragment implements PlanView, OnPlanClickListener {

    private static final String TAG = "PlanFragment";
   CalendarView calendarView;
   Calendar calendar;
   RecyclerView plannedMealsRecycler;
   PlanAdapter planAdapter;
   PlanPresenter presenter;
   ImageView empty;
   View view;
    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        empty = view.findViewById(R.id.iv_empty);
        plannedMealsRecycler = view.findViewById(R.id.rv_planned_meals);
        setupPresenter();

        calendarView = view.findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();

        calendarView.setDate(System.currentTimeMillis());
        String selectedDate = formatDate(calendar);
        presenter.getPlannedMeals(selectedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String newSelectedDate = formatDate(calendar);
                presenter.getPlannedMeals(newSelectedDate);
                //Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupPresenter() {
        Repository repository = RepositoryImpl.getInstance(
                CategoriesRemoteDataSourceImpl.getInstance(),
                AuthServiceImpl.getInstance(),
                IngredientsRemoteDataSourceImpl.getInstance(),
                AreaRemoteDataSourceImpl.getInstance(),
                RandomMealRemoteDataSourceImpl.getInstance(),
                CategoryFilterRemoteDataSourceImpl.getInstance(),
                IngredientFilterRemoteDataSourceImpl.getInstance(),
                AreaFilterRemoteDataSourceImpl.getInstance(),
                MealDetailsRemoteDataSourceImpl.getInstance(),
                MealDetailsLocalDataSourceImpl.getInstance(getContext())
        );
        presenter= new PlanPresenterImpl(repository, this);
    }
    public String formatDate(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void showPlannedMeals(List<MealDetails> mealDetailsList) {
        if(!mealDetailsList.isEmpty()){
            empty.setVisibility(View.INVISIBLE);
            plannedMealsRecycler.setVisibility(View.VISIBLE);
            planAdapter = new PlanAdapter(view.getContext(), this);
            plannedMealsRecycler.setAdapter(planAdapter);
            planAdapter.setList(mealDetailsList);
            Log.i(TAG, "showPlannedMeals: "+mealDetailsList.get(0).getStrMeal());
        }
        else{
            empty.setVisibility(View.VISIBLE);
            plannedMealsRecycler.setVisibility(View.INVISIBLE);
            Log.i(TAG, "showPlannedMeals: "+"No Meals Planned");
        }
    }

    @Override
    public void showErrMsg(String msg) {
        Log.i(TAG, "onFailure: " + msg);
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlannedMealClick(MealDetails mealDetails) {
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action
                = PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment("", mealDetails);
        Navigation.findNavController(view).navigate(action);
    }
}
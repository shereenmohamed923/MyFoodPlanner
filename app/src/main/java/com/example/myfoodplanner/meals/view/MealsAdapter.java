package com.example.myfoodplanner.meals.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.model.filter.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsHolder> {
    Context context;
    List<Meal> meals = new ArrayList<>();
    private OnMealClickListener listener;

    public MealsAdapter(Context context, OnMealClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setMealsList(List<Meal> meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_categories_item_list, parent, false);
        MealsHolder holder = new MealsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.title.setText(meal.getStrMeal());
        String url = meal.getStrMealThumb();
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        holder.mealCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
class MealsHolder extends RecyclerView.ViewHolder{
    View convertView;
    CardView mealCategoryCard;
    ImageView thumbnail;
    TextView title;
    public MealsHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        mealCategoryCard = itemView.findViewById(R.id.cv_meal_category_item);
        thumbnail = itemView.findViewById(R.id.iv_meal_category);
        title = itemView.findViewById(R.id.tv_meal_category_name);
    }
}

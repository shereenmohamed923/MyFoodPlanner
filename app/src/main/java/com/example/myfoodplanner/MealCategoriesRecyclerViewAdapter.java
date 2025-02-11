package com.example.myfoodplanner;

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

import java.util.ArrayList;
import java.util.List;

public class MealCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<MealCategoriesRecyclerViewHolder> {
    Context context;
    List<MealCategory> mealCategories;

    public MealCategoriesRecyclerViewAdapter(Context context, List<MealCategory> mealCategories) {
        this.context = context;
        this.mealCategories = mealCategories;
    }

    @NonNull
    @Override
    public MealCategoriesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflater & create recycler view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_categories_item_list, parent, false);
        MealCategoriesRecyclerViewHolder holder = new MealCategoriesRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealCategoriesRecyclerViewHolder holder, int position) {
        //populate data
        MealCategory mealCategory = mealCategories.get(position);
        holder.title.setText(mealCategory.getStrCategory());
        String url = mealCategory.getStrCategoryThumb();
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        //event handling

    }

    @Override
    public int getItemCount() {
        return mealCategories.size();
    }
}
    class MealCategoriesRecyclerViewHolder extends RecyclerView.ViewHolder{
        View convertView;
        CardView mealCategoryCard;
        ImageView thumbnail;
        TextView title;

     public MealCategoriesRecyclerViewHolder(@NonNull View itemView) {
         super(itemView);
         convertView = itemView;
         mealCategoryCard = itemView.findViewById(R.id.cv_meal_category_item);
         thumbnail = itemView.findViewById(R.id.iv_meal_category);
         title = itemView.findViewById(R.id.tv_meal_category_name);
     }
 }

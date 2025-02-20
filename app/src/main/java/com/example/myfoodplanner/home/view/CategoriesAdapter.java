package com.example.myfoodplanner.home.view;

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
import com.example.myfoodplanner.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesHolder> {
    Context context;
    List<Category> categories = new ArrayList<>();
    private OnListClickListener listener;
    public CategoriesAdapter(Context context, OnListClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

   public void setCategoriesList(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
   }
    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflater & create recycler view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_categories_item_list, parent, false);
        CategoriesHolder holder = new CategoriesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        //populate data
        Category category = categories.get(position);
        holder.title.setText(category.getStrCategory());
        String url = category.getStrCategoryThumb();
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        //event handling
        holder.mealCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category.getStrCategory(), "c");
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
    class CategoriesHolder extends RecyclerView.ViewHolder{
        View convertView;
        CardView mealCategoryCard;
        ImageView thumbnail;
        TextView title;

     public CategoriesHolder(@NonNull View itemView) {
         super(itemView);
         convertView = itemView;
         mealCategoryCard = itemView.findViewById(R.id.cv_meal_category_item);
         thumbnail = itemView.findViewById(R.id.iv_meal_category);
         title = itemView.findViewById(R.id.tv_meal_category_name);
     }
 }

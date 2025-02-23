package com.example.myfoodplanner.home.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.example.myfoodplanner.model.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsHolder> {
    Context context;
    List<Ingredient> ingredients = new ArrayList<>();
    private OnListClickListener listener;

    public IngredientsAdapter(Context context, OnListClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setIngredientsList(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
    private static final int[] COLORS = {
            Color.parseColor("#F5D6CE"),
            Color.parseColor("#FEF8E5"),
            Color.parseColor("#D3EBC7"),
            Color.parseColor("#F4EBF7"),
            Color.parseColor("#FFF6EE"),
            Color.parseColor("#D8E3F4")
    };

    @NonNull
    @Override
    public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.circle_card_item, parent, false);
        IngredientsHolder holder = new IngredientsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        int color = COLORS[position % COLORS.length];
        holder.mealCategoryCard.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.title.setText(ingredient.getStrIngredient());
        String url = "https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png";
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        //event handling
        holder.mealCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIngredientClick(ingredient.getStrIngredient(), "i");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
class IngredientsHolder extends  RecyclerView.ViewHolder{
    View convertView;
    CardView mealCategoryCard;
    ImageView thumbnail;
    TextView title;
    public IngredientsHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        mealCategoryCard = itemView.findViewById(R.id.cv_circular_card);
        thumbnail = itemView.findViewById(R.id.iv_circle_img);
        title = itemView.findViewById(R.id.tv_circle_title);
    }
}

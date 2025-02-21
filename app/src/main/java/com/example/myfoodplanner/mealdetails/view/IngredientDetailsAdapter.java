package com.example.myfoodplanner.mealdetails.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplanner.R;
import com.example.myfoodplanner.model.mealdetails.IngredientDetails;

import java.util.ArrayList;
import java.util.List;

public class IngredientDetailsAdapter extends RecyclerView.Adapter<IngredientDetailsHolder> {
   Context context;
   List<IngredientDetails> ingredientsDetails = new ArrayList<>();
    public IngredientDetailsAdapter(Context context) {
        this.context = context;
    }
    public void setIngredientDetails(List<IngredientDetails> ingredientDetails){
        this.ingredientsDetails = ingredientDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        IngredientDetailsHolder holder = new IngredientDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientDetailsHolder holder, int position) {
        IngredientDetails ingredient = ingredientsDetails.get(position);
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientMeasurement.setText(ingredient.getMeasurement());
        if(ingredient.getName() != null){
            String url = "https://www.themealdb.com/images/ingredients/"+ingredient.getName()+"-Small.png";
            Glide.with(context).load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.ingredientImage);
        }
        Log.i("ingredients adapter", "ingredient : "+ingredient.getName());
    }

    @Override
    public int getItemCount() {
        return ingredientsDetails.size();
    }
}
class IngredientDetailsHolder extends RecyclerView.ViewHolder{
    View converView;
    ImageView ingredientImage;
    TextView ingredientName;
    TextView ingredientMeasurement;
    public IngredientDetailsHolder(@NonNull View itemView) {
        super(itemView);
        converView = itemView;
        ingredientImage = itemView.findViewById(R.id.iv_ingredient_img);
        ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
        ingredientMeasurement = itemView.findViewById(R.id.tv_measurement);
    }
}

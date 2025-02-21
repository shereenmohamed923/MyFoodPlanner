package com.example.myfoodplanner.mealdetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplanner.R;
import com.example.myfoodplanner.model.mealdetails.IngredientDetails;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsHolder> {
    Context context;
    List<String> steps = new ArrayList<>();
    public RecipeStepsAdapter(Context context){
        this.context = context;
    }
    public void setRecipeSteps(List<String> recipeSteps){
        this.steps = recipeSteps;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecipeStepsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.instructions_item, parent, false);
        RecipeStepsHolder holder = new RecipeStepsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsHolder holder, int position) {
        holder.stepDescription.setText(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
class RecipeStepsHolder extends RecyclerView.ViewHolder{
    View convertView;
    TextView stepDescription;
    public RecipeStepsHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        stepDescription = itemView.findViewById(R.id.tv_step_description);
    }
}

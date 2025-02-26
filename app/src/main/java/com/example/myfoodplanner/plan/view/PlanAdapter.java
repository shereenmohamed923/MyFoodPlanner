package com.example.myfoodplanner.plan.view;

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
import com.example.myfoodplanner.model.mealdetails.MealDetails;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanHolder> {
    private Context context;
    private List<MealDetails> mealDetailsList = new ArrayList<>();
    private OnPlanClickListener listener;
    public PlanAdapter(Context context, OnPlanClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setList(List<MealDetails> plannedMeals){
        this.mealDetailsList = plannedMeals;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.big_square_card, parent, false);
        PlanHolder holder = new PlanHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanHolder holder, int position) {
        if(!mealDetailsList.isEmpty()){
            MealDetails currentMeal = mealDetailsList.get(position);
            holder.mealName.setText(currentMeal.getStrMeal());
            String url = currentMeal.getStrMealThumb();
            Glide.with(context).load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.mealImg);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPlannedMealClick(currentMeal);
                }
            });
    }
        }

    @Override
    public int getItemCount() {
        return mealDetailsList.size();
    }
}
class PlanHolder extends RecyclerView.ViewHolder{
    View convertView;
    ImageView mealImg;
    TextView mealName;
    CardView card;
    public PlanHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        card = itemView.findViewById(R.id.cv_big_square_card);
        mealImg = itemView.findViewById(R.id.iv_big_square_card);
        mealName = itemView.findViewById(R.id.tv_big_square_card);
    }
}

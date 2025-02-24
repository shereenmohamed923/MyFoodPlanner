package com.example.myfoodplanner.favourites.view;

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

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesHolder> {
    private Context context;
    private List<MealDetails> mealDetailsList = new ArrayList<>();
    private OnFavProductClickListener listener;

    public FavouritesAdapter(Context context, OnFavProductClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setList(List<MealDetails> updatedFavourites){
        this.mealDetailsList = updatedFavourites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.square_card_item, parent, false);
        FavouritesHolder holder = new FavouritesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesHolder holder, int position) {
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
                    listener.onFavMealClick(currentMeal);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return mealDetailsList.size();
    }
}
class FavouritesHolder extends RecyclerView.ViewHolder{
    View convertView;
    ImageView mealImg;
    TextView mealName;
    CardView card;

    public FavouritesHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        card = itemView.findViewById(R.id.cv_square_card);
        mealImg = itemView.findViewById(R.id.iv_square_img);
        mealName = itemView.findViewById(R.id.tv_square_title);
    }
}

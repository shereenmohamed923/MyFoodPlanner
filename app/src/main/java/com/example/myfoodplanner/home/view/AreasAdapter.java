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
import com.example.myfoodplanner.model.area.Area;
import com.example.myfoodplanner.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasHolder> {
    Context context;
    List<Area> areas = new ArrayList<>();
    private OnMealClickListener listener;
    public AreasAdapter(Context context, OnMealClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setAreasList(List<Area> areas){
        this.areas = areas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AreasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflater & create recycler view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_categories_item_list, parent, false);
        AreasHolder holder = new AreasHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AreasHolder holder, int position) {
        //populate data
        Area area = areas.get(position);
        holder.title.setText(area.getStrArea());
        String url = "https://www.themealdb.com/images/media/meals/ssrrrs1503664277.jpg";
        Glide.with(context).load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.thumbnail);

        holder.mealCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClick(area);
                //home fragment will implement this
//                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment3 action
//                        = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment3(mealCategories.get(holder.getAdapterPosition()).getIdCategory());
//                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }
}

class AreasHolder extends RecyclerView.ViewHolder{
    View convertView;
    CardView mealCategoryCard;
    ImageView thumbnail;
    TextView title;
    public AreasHolder(@NonNull View itemView) {
        super(itemView);
        convertView = itemView;
        mealCategoryCard = itemView.findViewById(R.id.cv_meal_category_item);
        thumbnail = itemView.findViewById(R.id.iv_meal_category);
        title = itemView.findViewById(R.id.tv_meal_category_name);
    }
}

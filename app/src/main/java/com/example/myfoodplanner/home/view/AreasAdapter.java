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

import com.example.myfoodplanner.R;
import com.example.myfoodplanner.model.area.Area;

import java.util.ArrayList;
import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasHolder> {
    Context context;
    List<Area> areas = new ArrayList<>();
    List<Integer> thumbnails = new ArrayList<>();
    private OnListClickListener listener;
    public AreasAdapter(Context context, OnListClickListener listener) {
        this.context = context;
        this.listener = listener;
        initializeThumbnails();
    }

    private void initializeThumbnails(){
        thumbnails.add(R.drawable.america);
        thumbnails.add(R.drawable.britain);
        thumbnails.add(R.drawable.canada);
        thumbnails.add(R.drawable.china);
        thumbnails.add(R.drawable.coratia);
        thumbnails.add(R.drawable.dutch);
        thumbnails.add(R.drawable.egypt);
        thumbnails.add(R.drawable.philippine);
        thumbnails.add(R.drawable.france);
        thumbnails.add(R.drawable.greece);
        thumbnails.add(R.drawable.india);
        thumbnails.add(R.drawable.ireland);
        thumbnails.add(R.drawable.italy);
        thumbnails.add(R.drawable.jamaica);
        thumbnails.add(R.drawable.japan);
        thumbnails.add(R.drawable.kenya);
        thumbnails.add(R.drawable.malaysia);
        thumbnails.add(R.drawable.mexico);
        thumbnails.add(R.drawable.morocco);
        thumbnails.add(R.drawable.poland);
        thumbnails.add(R.drawable.portugal);
        thumbnails.add(R.drawable.russia);
        thumbnails.add(R.drawable.spain);
        thumbnails.add(R.drawable.thailand);
        thumbnails.add(R.drawable.tunisia);
        thumbnails.add(R.drawable.turkey);
        thumbnails.add(R.drawable.ukraine);
        thumbnails.add(R.drawable.uruguay);
        thumbnails.add(R.drawable.vietnam);
    }

    public void setAreasList(List<Area> areas){
        this.areas = areas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AreasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.flag_card_item, parent, false);
        AreasHolder holder = new AreasHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AreasHolder holder, int position) {
        //populate data
        Area area = areas.get(position);
        holder.title.setText(area.getStrArea());
        holder.thumbnail.setImageResource(thumbnails.get(position));

        holder.mealCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAreaClick(area.getStrArea(), "a");
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
        mealCategoryCard = itemView.findViewById(R.id.cv_flag_card);
        thumbnail = itemView.findViewById(R.id.iv_flag_img);
        title = itemView.findViewById(R.id.tv_flag_title);
    }
}

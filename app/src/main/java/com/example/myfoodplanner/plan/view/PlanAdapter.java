package com.example.myfoodplanner.plan.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlanAdapter extends RecyclerView.Adapter<PlanHolder> {
    @NonNull
    @Override
    public PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class PlanHolder extends RecyclerView.ViewHolder{

    public PlanHolder(@NonNull View itemView) {
        super(itemView);
    }
}

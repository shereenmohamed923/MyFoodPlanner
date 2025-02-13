//package com.example.myfoodplanner;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//public class FilterByCategoriesAdapter extends RecyclerView.Adapter<FilterByCategoriesHolder> {
//    Context context;
//    List<MealCategory> mealCategories;
//
//    public FilterByCategoriesAdapter(Context context, List<MealCategory> mealCategories) {
//        this.context = context;
//        this.mealCategories = mealCategories;
//    }
//
//    @NonNull
//    @Override
//    public FilterByCategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //layout inflater & create recycler view holder
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.category_item, parent, false);
//        FilterByCategoriesHolder holder = new FilterByCategoriesHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FilterByCategoriesHolder holder, int position) {
//        //populate data
//        MealCategory mealCategory = mealCategories.get(position);
//        holder.title.setText(mealCategory.getStrCategory());
//        String url = mealCategory.getStrCategoryThumb();
//        Glide.with(context).load(url)
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.categoryImage);
//
//        //event handling
//        holder.convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment3 action
//                        = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment3(mealCategories.get(holder.getAdapterPosition()).getIdCategory());
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mealCategories.size();
//    }
//}
//
//class FilterByCategoriesHolder extends RecyclerView.ViewHolder{
//    View convertView;
//    ImageView categoryImage;
//    TextView title;
//
//    public FilterByCategoriesHolder(@NonNull View itemView) {
//        super(itemView);
//        convertView = itemView;
//        categoryImage = itemView.findViewById(R.id.iv_avatar);
//        title = itemView.findViewById(R.id.tv_title);
//    }
//}

package com.project.foodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.foodrecipes.R;
import com.project.foodrecipes.model.ModelFilter;

import java.util.List;

/**
 * Created by Achmad Qomarudin on 19-10-2020.
 */

public class FilterFoodAdapter extends RecyclerView.Adapter<FilterFoodAdapter.ViewHolder> {

    private List<ModelFilter> items;
    private FilterFoodAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData {
        void onSelected(ModelFilter modelMain);
    }

    public FilterFoodAdapter(Context context, List<ModelFilter> items, FilterFoodAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_filter_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelFilter data = items.get(position);

        //Get Image
        Glide.with(mContext)
                .load(data.strMealThumb)
                .placeholder(R.drawable.ic_food_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgThumb);

        holder.tvMeal.setText(data.strMeal);
        holder.cvFilterMeal.setOnClickListener(v -> onSelectData.onSelected(data));
        holder.imgFavorite.setOnClickListener(v -> {
            Toast.makeText(mContext, "Feature under development", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Class Holder
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMeal;
        public CardView cvFilterMeal;
        public ImageView imgThumb, imgFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            cvFilterMeal = itemView.findViewById(R.id.cvFilterMeal);
            tvMeal       = itemView.findViewById(R.id.tvMeal);
            imgThumb     = itemView.findViewById(R.id.imgThumb);
            imgFavorite  = itemView.findViewById(R.id.imgFavorite);
        }
    }

}

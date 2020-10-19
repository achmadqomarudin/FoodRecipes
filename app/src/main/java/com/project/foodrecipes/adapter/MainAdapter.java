package com.project.foodrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.foodrecipes.R;
import com.project.foodrecipes.model.ModelMain;

import java.util.List;

/**
 * Created by Achmad Qomarudin on 19-10-2020.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ModelMain> items;
    private MainAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData {
        void onSelected(ModelMain modelMain);
    }

    public MainAdapter(Context context, List<ModelMain> items, MainAdapter.onSelectData xSelectData) {
        this.mContext = context;
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_categories, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelMain data = items.get(position);

        //Get Image
        Glide.with(mContext)
                .load(data.strCategoryThumb)
                .placeholder(R.drawable.ic_food_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgKategori);

        holder.tvKategori.setText(data.strCategory);
        holder.cvKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Class Holder
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvKategori;
        public CardView cvKategori;
        public ImageView imgKategori;

        public ViewHolder(View itemView) {
            super(itemView);
            cvKategori = itemView.findViewById(R.id.cvKategori);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            imgKategori = itemView.findViewById(R.id.imgKategori);
        }
    }

}

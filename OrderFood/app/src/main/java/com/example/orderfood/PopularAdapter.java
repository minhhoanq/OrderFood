package com.example.orderfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.model.Popular;

import java.io.Serializable;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<Popular> popularList;

    public PopularAdapter(List<Popular> popularList) {
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Popular popular = popularList.get(position);
        int id = position;

        if(popular == null) return;

        holder.title.setText(popular.getTitle());
        holder.image.setImageResource(popular.getImg_popular());
        holder.price.setText(popular.getPrice());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                c.startActivity(new Intent(c, DetailActivity.class)
                        .putExtra("keyname", (Serializable) popularList)
                        .putExtra("idobj", id));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(popularList != null) return popularList.size();
        return 0;
    }

    static class PopularViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView image;
        ConstraintLayout layout;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_add_item);
            title = itemView.findViewById(R.id.title_item_popular);
            price = itemView.findViewById(R.id.tv_price);
            image = itemView.findViewById(R.id.img_popular);
        }
    }
}

package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.DetailActivity;
import com.example.orderfood.MyCartActivity;
import com.example.orderfood.R;
import com.example.orderfood.model.Product;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<Product> listProduct;

    public PopularAdapter(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Product product = listProduct.get(position);
        int id = position;

        if(product == null) return;

        holder.title.setText(product.getTenSP());
        holder.price.setText(String.valueOf(product.getDonGia()));

        String url = product.getHinhAnh();
        Log.d("IMG", url + "");
        Glide.with(holder.image.getContext())
                .load(url)
                .into(holder.image);

        String key = "info_nuoc_tang_luc";
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                if(MyCartActivity.listGHGlobal.size() != 0){
                    for(int i = 0; i < MyCartActivity.listGHGlobal.size(); i++){
                        if(MyCartActivity.listGHGlobal.get(i).getMASP() == product.getMaSP()) {
                            check = 1;
                            break;
                        }
                    }
                }
                if(check != 1) {
                    Context c = v.getContext();
                    c.startActivity(new Intent(c, DetailActivity.class)
                            .putExtra("id_key", key)
                            .putExtra(key, product)
                    );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listProduct != null) return listProduct.size();
        return 0;
    }

    static class PopularViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView image;
        ConstraintLayout layoutItem;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_add_nuoc_tang_luc);
            title = itemView.findViewById(R.id.title_nuoc_tang_luc);
            price = itemView.findViewById(R.id.tv_price_nuoc_tang_luc);
            image = itemView.findViewById(R.id.img_nuoc_tang_luc);
        }
    }
}

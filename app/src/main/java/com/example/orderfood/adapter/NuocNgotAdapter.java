package com.example.orderfood.adapter;

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

import com.bumptech.glide.Glide;
import com.example.orderfood.DetailActivity;
import com.example.orderfood.MyCartActivity;
import com.example.orderfood.R;
import com.example.orderfood.model.Product;

import java.util.List;

public class NuocNgotAdapter extends RecyclerView.Adapter<NuocNgotAdapter.NuocNgotViewHolder> {
    private List<Product> listProdutc;

    public NuocNgotAdapter(List<Product> listProdutc) {
        this.listProdutc = listProdutc;
    }

    @NonNull
    @Override
    public NuocNgotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_nuoc_ngot, parent, false);
        return new NuocNgotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NuocNgotViewHolder holder, int position) {
        Product product =  listProdutc.get(position);

        if(product == null) return;

        holder.name.setText(product.getTenSP());
        holder.price.setText(String.valueOf(product.getDonGia()));
        String url = product.getHinhAnh();
        Glide.with(holder.img.getContext())
                .load(url)
                .into(holder.img);

        String key = "info_nuoc_ngot";

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
        if(listProdutc != null) return listProdutc.size();
        return 0;
    }

    public class NuocNgotViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img;
        ConstraintLayout layoutItem;
        public NuocNgotViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title_item_nuoc_ngot);
            price = itemView.findViewById(R.id.tv_price_nuoc_ngot);
            img = itemView.findViewById(R.id.img_nuoc_ngot);
            layoutItem = itemView.findViewById(R.id.layout_add_nuoc_ngot);
        }
    }
}

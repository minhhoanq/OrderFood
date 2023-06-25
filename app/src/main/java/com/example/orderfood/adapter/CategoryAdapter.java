package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.orderfood.DetailActivity;
import com.example.orderfood.MyCartActivity;
import com.example.orderfood.R;
import com.example.orderfood.model.Product;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    private List<Product> listNuocSuoi;

    public CategoryAdapter(List<Product> listNuocSuoi) {
        this.listNuocSuoi = listNuocSuoi;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_item_category, parent, false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        Product product = listNuocSuoi.get(position);

        if(product == null) return;

        holder.name.setText(product.getTenSP());
        holder.price.setText(String.valueOf(product.getDonGia()));

        String url = product.getHinhAnh();
        Glide.with(holder.image.getContext())
                .load(url)
                .into(holder.image);

        String key = "info_nuoc_suoi";

        holder.layout.setOnClickListener(new View.OnClickListener() {
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
        if(listNuocSuoi != null) return listNuocSuoi.size();
        return 0;
    }

    static class CategoryViewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private ConstraintLayout layout;
        private TextView price;
        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_nuoc_suoi);
            name = itemView.findViewById(R.id.title_item_nuoc_suoi);
            layout = itemView.findViewById(R.id.layout_add_nuoc_suoi);
            price = itemView.findViewById(R.id.tv_price_nuoc_suoi);
        }
    }
}

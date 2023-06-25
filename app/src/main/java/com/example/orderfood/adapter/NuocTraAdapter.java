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

import org.w3c.dom.Text;

import java.util.List;

public class NuocTraAdapter extends RecyclerView.Adapter<NuocTraAdapter.NuocTraViewHolder> {
    private List<Product> listNuocTra;

    public NuocTraAdapter(List<Product> listNuocTra) {
        this.listNuocTra = listNuocTra;
    }

    @NonNull
    @Override
    public NuocTraAdapter.NuocTraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_nuoc_tra, parent, false);
        return new NuocTraAdapter.NuocTraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NuocTraAdapter.NuocTraViewHolder holder, int position) {
        Product product = listNuocTra.get(position);

        if(product == null) return;

        holder.name.setText(product.getTenSP());
        holder.price.setText(String.valueOf(product.getDonGia()));

        String url = product.getHinhAnh();
        Glide.with(holder.img.getContext())
                .load(url)
                .into(holder.img);

        String key = "info_nuoc_tra";
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
        if(listNuocTra != null) return listNuocTra.size();
        return 0;
    }

    public class NuocTraViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ConstraintLayout layoutItem;
        ImageView img;
        public NuocTraViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title_item_nuoc_tra);
            price = itemView.findViewById(R.id.tv_price_nuoc_tra);
            img = itemView.findViewById(R.id.img_nuoc_tra);
            layoutItem = itemView.findViewById(R.id.layout_add_nuoc_tra);
        }
    }
}

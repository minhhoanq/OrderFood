package com.example.orderfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    private List<Category> listCategory;

    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_item_category, parent, false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        Category category = listCategory.get(position);

        if(category == null) return;

        holder.name.setText(category.getNamecategory());
        holder.image.setImageResource(category.getImgcategory());
    }

    @Override
    public int getItemCount() {
        if(listCategory != null) return listCategory.size();
        return 0;
    }

    static class CategoryViewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private ConstraintLayout layout;
        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_food);
            name = itemView.findViewById(R.id.name_food_cate);
            layout = itemView.findViewById(R.id.layout_ct_cate);
        }
    }
}

package com.example.orderfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.model.Popular;

import java.util.ArrayList;


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {
    private ArrayList<Popular> listMyCart;
    private ManagementCart managementCart;
    private ChangeNumberItem changeNumberItem;

    public MyCartAdapter(){}

    public MyCartAdapter(ArrayList<Popular> listMyCart, Context context, ChangeNumberItem changeNumberItem) {
        this.listMyCart = listMyCart;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItem = changeNumberItem;
    }

    @NonNull
    @Override
    public MyCartAdapter.MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_cart, parent, false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.MyCartViewHolder holder, int position) {
        Popular myCart = listMyCart.get(position);
        int id = position;

        holder.hinhCart.setImageResource(myCart.getImg_popular());
        holder.priceCart.setText(myCart.getPrice());
        holder.titleCart.setText(myCart.getTitle());
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusCountFood(listMyCart, id, new ChangeNumberItem() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItem.changed();
                    }
                });
            }
        });

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusCountFood(listMyCart, id, new ChangeNumberItem() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItem.changed();
                    }
                });
            }
        });

        holder.countCart.setText(String.valueOf(myCart.getCount()));
        myCart.setTotal((Math.round(managementCart.getTotalFood(listMyCart, id)*100.0)/100.0));
        holder.tvPayment.setText(String.valueOf(myCart.getTotal()));
    }

    @Override
    public int getItemCount() {
        if(listMyCart != null) return listMyCart.size();
        return 0;
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhCart;
        TextView countCart, priceCart, titleCart;
        TextView tvPayment;
        ImageView imgMinus, imgPlus;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhCart = itemView.findViewById(R.id.img_Cart);
            countCart = itemView.findViewById(R.id.tv_count_cart);
            priceCart = itemView.findViewById(R.id.tv_price_cart);
            titleCart = itemView.findViewById(R.id.cs_item_title);
            tvPayment = itemView.findViewById(R.id.tv_pay_ment);
            imgMinus = itemView.findViewById(R.id.img_minus_cart);
            imgPlus = itemView.findViewById(R.id.img_plus_cart);
        }
    }
}

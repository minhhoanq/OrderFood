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
import com.example.orderfood.ChangeNumberItem;
import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.ManagementCart;
import com.example.orderfood.MyCartActivity;
import com.example.orderfood.R;
import com.example.orderfood.model.GioHang;
import com.example.orderfood.model.Popular;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {
    private List<GioHang> listMyCart;
    private TextView tvtotalItem,tvTotalAll, tvmgg;
    private ConstraintLayout btnCheck;
    private ImageView ghtrong;

    public MyCartAdapter(List<GioHang> listMyCart, TextView tvtotalItem, TextView tvTotalAll, ConstraintLayout btnCheck, ImageView ghtrong, TextView tvmgg) {
        this.listMyCart = listMyCart;
        this.tvtotalItem = tvtotalItem;
        this.tvTotalAll = tvTotalAll;
        this.btnCheck = btnCheck;
        this.ghtrong = ghtrong;
        this.tvmgg = tvmgg;
    }

    @NonNull
    @Override
    public MyCartAdapter.MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_cart, parent, false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.MyCartViewHolder holder, int position) {
        GioHang gh = listMyCart.get(position);
        int id = position;

        holder.priceCart.setText(String.valueOf(gh.getTongTien()));
        holder.titleCart.setText(gh.getTenSP());
        holder.tvSoluong.setText(String.valueOf(gh.getSoLuong()));

        Glide.with(holder.hinhCart.getContext())
                .load(gh.getAnhSp())
                .into(holder.hinhCart);

        holder.priceCart.setText(String.valueOf(gh.getTienGoc()));
        holder.tvPayment.setText(String.valueOf(gh.getTongTien()));
//
//        gh.setTotal((Math.round(managementCart.getTotalFood(listMyCart, id)*100.0)/100.0));

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < MyCartActivity.listGHGlobal.size(); i++){
                    if(MyCartActivity.listGHGlobal.get(i).getMASP() == gh.getMASP()){
                        MyCartActivity.listGHGlobal.remove(i);
                        notifyItemRemoved(id);
                        float sum = 0;
                        for(int j = 0; j < MyCartActivity.listGHGlobal.size(); j++){
                            sum += MyCartActivity.listGHGlobal.get(j).getTongTien();
                        }

                        //Check money total
                        if(sum == 0) {
                            tvmgg.setText("Không áp dụng.");
                            tvtotalItem.setText(String.valueOf(0));
                            tvTotalAll.setText(String.valueOf(0));
                        }else{
                            tvtotalItem.setText(String.valueOf(sum));
                            if (sum < 50000) {
                                tvmgg.setText("Không áp dụng.");
                                tvTotalAll.setText(String.valueOf(sum));
                            } else if (sum < 100000) {
                                tvTotalAll.setText(String.valueOf(sum - 20000));
                            } else  if (sum < 200000) {
                                tvTotalAll.setText(String.valueOf(sum - 30000));
                            } else  if (sum < 300000) {
                                tvTotalAll.setText(String.valueOf(sum - 50000));
                            } else if (sum < 400000) {
                                tvTotalAll.setText(String.valueOf(sum - 70000));
                            } else if (sum < 500000) {
                                tvTotalAll.setText(String.valueOf(sum - 90000));
                            } else if (sum < 600000) {
                                tvTotalAll.setText(String.valueOf(sum - 11000));
                            } else if (sum < 700000) {
                                tvTotalAll.setText(String.valueOf(sum - 130000));
                            } else if (sum < 900000) {
                                tvTotalAll.setText(String.valueOf(sum - 150000));
                            } else if (sum < 1000000) {
                                tvTotalAll.setText(String.valueOf(sum - 180000));
                            } else {
                                tvTotalAll.setText(String.valueOf(sum - 200000));
                            }
                        }

                        InsertKM(sum);

                        //Check list product
                        if(MyCartActivity.listGHGlobal.isEmpty()){
                            btnCheck.setVisibility(View.INVISIBLE);
                            ghtrong.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(listMyCart != null) return listMyCart.size();
        return 0;
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhCart;
        TextView  priceCart, titleCart;
        TextView tvPayment, tvSoluong;
        ImageView btnRemove;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhCart = itemView.findViewById(R.id.img_Cart);
            priceCart = itemView.findViewById(R.id.tv_price_cart);
            titleCart = itemView.findViewById(R.id.cs_item_title);
            tvPayment = itemView.findViewById(R.id.tv_pay_ment);
            tvSoluong = itemView.findViewById(R.id.tv_sl_cart);
            btnRemove = itemView.findViewById(R.id.btn_remove_item);
        }
    }

    private void InsertKM(float sumTotal) {
        int km = 0;
        if (sumTotal < 50000) {
            km = 0;
        } else if (sumTotal < 100000) {
            km = 1;
        } else  if (sumTotal < 200000) {
            km = 2;
        } else  if (sumTotal < 300000) {
            km = 3;
        } else if (sumTotal < 400000) {
            km = 4;
        } else if (sumTotal < 500000) {
            km = 5;
        } else if (sumTotal < 600000) {
            km = 6;
        } else if (sumTotal < 700000) {
            km = 7;
        } else if (sumTotal < 900000) {
            km = 8;
        } else if (sumTotal < 1000000) {
            km = 9;
        } else {
            km = 10;
        }
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "SELECT * FROM CT_KHUYENMAI Where MA_LKM = " + km;
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    tvmgg.setText(rs.getString("CTKM"));
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
        } catch (Exception ex) {
        }
    }
}

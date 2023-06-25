package com.example.orderfood.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customerList;
    dbConnection dbConnect;

    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_cus, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        if(customer == null) return;

        holder.tvTen.setText(customer.getHo() + " " + customer.getTen());

        int gt = customer.getPhai();

        if(gt == 2) {
            holder.tvGioiTinh.setText("Nữ");
        }else{
            holder.tvGioiTinh.setText("Nam");
        }


        holder.tvChucVu.setText("Khách hàng");


        if(customer.getTrangthai() == 0) {
            holder.swCheck.setChecked(true);
            holder.tvTrangthai.setText("Đang hoạt động");
            holder.textCv.setTextColor(Color.parseColor("#2196F3"));
            holder.textTt.setTextColor(Color.parseColor("#2196F3"));
            holder.imgDotChange.setImageResource(R.drawable.cs_dot);
        }else if(customer.getTrangthai() == 1){
            holder.swCheck.setChecked(false);
            holder.tvTrangthai.setText("Ngừng hoạt động");
            holder.textCv.setTextColor(Color.GRAY);
            holder.textTt.setTextColor(Color.GRAY);
            holder.imgDotChange.setImageResource(R.drawable.cs_dot_red);
        }

        holder.swCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = customerList.get(position).getTrangthai() == 0;
                if(isChecked) {
                    customerList.get(position).setTrangthai(1);
                    holder.tvTrangthai.setText("Ngừng hoạt động");
                    holder.textCv.setTextColor(Color.GRAY);
                    holder.textTt.setTextColor(Color.GRAY);
                    holder.imgDotChange.setImageResource(R.drawable.cs_dot_red);
                    UpdateTT(customer, customer.getTrangthai());
                    holder.swCheck.setChecked(false);
                }else {
                    customerList.get(position).setTrangthai(0);
                    holder.tvTrangthai.setText("Đang hoạt động");
                    holder.textCv.setTextColor(Color.parseColor("#2196F3"));
                    holder.textTt.setTextColor(Color.parseColor("#2196F3"));
                    holder.imgDotChange.setImageResource(R.drawable.cs_dot);
                    UpdateTT(customer, customer.getTrangthai());
                    holder.swCheck.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(customerList != null) return customerList.size();
        return 0;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTen, tvGioiTinh, tvChucVu, tvTrangthai, textCv, textTt;
        private ConstraintLayout btnShowCus;
        private SwitchCompat swCheck;
        private ImageView imgDotChange;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tv_name_cus);
            tvGioiTinh = itemView.findViewById(R.id.tv_gt_cus);
            tvChucVu = itemView.findViewById(R.id.tv_cv_cus);
            tvTrangthai = itemView.findViewById(R.id.tv_tt_cus);
            swCheck = itemView.findViewById(R.id.sw_check_tt_cus);
            imgDotChange = itemView.findViewById(R.id.img_dot_change_cus);
            textCv = itemView.findViewById(R.id.tv_text_cv_cus);
            textTt = itemView.findViewById(R.id.tv_text_tt_cus);

            btnShowCus = itemView.findViewById(R.id.btn_show_info_cus);
        }
    }

    private void UpdateTT(Customer customer, int tt) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update KHACHHANG set TRANGTHAI = ? where EMAIL = " + "'" + customer.getEmail() + "'";

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(tt)));

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi");
        }
    }
}

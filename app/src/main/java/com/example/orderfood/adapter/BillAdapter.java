package com.example.orderfood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.model.Bill;
import com.example.orderfood.model.ChiTietDDH;
import com.example.orderfood.model.ChiTietHDKM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<Bill> listBill;
    Connection cons;
    dbConnection dbConnect;

    public BillAdapter(List<Bill> listBill) {
        this.listBill = listBill;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_bill_item, parent, false);
        return new BillAdapter.BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = listBill.get(position);
        if(bill == null) return;

        holder.soHD.setText(String.valueOf(bill.getmHD()));
        holder.ngay.setText(bill.getNgayHD());
        holder.tongtien.setText(String.valueOf(bill.getTongTien()));

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_ct_hd_km);

                TextView mahd = dialog.findViewById(R.id.tv_m_hd);
                TextView makm = dialog.findViewById(R.id.tv_m_km);

//                ChiTietHDKM chiTietHDKM = SelectBillDB(bill.getmHD());
//
//                mahd.setText(String.valueOf(chiTietHDKM.getMaHD()));
//                makm.setText(String.valueOf(chiTietHDKM.getmKM()));
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listBill != null) return listBill.size();
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView soHD, ngay, tongtien;
        ConstraintLayout layoutItem;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            soHD = itemView.findViewById(R.id.tv_sohd);
            ngay = itemView.findViewById(R.id.tv_ngay_hd);
            tongtien = itemView.findViewById(R.id.tv_tong_tien);
            layoutItem = itemView.findViewById(R.id.layout_item_hd);
        }
    }

//    public ChiTietHDKM SelectBillDB(int mhd) {
//        try {
//            cons = dbConnect.connectionClass();
//            String sql = "Select * From CT_HD_KM where MA_HD = " + mhd;
//            ChiTietHDKM cthdkm = new ChiTietHDKM();
//            Statement stmt = cons.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            if(rs != null) {
//                while (rs.next()) {
//                    cthdkm.setMaHD(rs.getInt("MA_HD"));
//                    cthdkm.setmKM(rs.getInt("MA_KM"));
//                }
//            }else {
//                Log.d("III", "vô");
//            }
//            rs.close();
//            stmt.close();
//            cons.close();
//            return cthdkm;
//        }catch (Exception e){
//            Log.d("III", "lỗi CTHDKM");
//        }
//        return null;
//    }
}

package com.example.orderfood.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.adapter.BillAdapter;
import com.example.orderfood.adapter.CategoryAdapter;
import com.example.orderfood.model.ACCOUNT;
import com.example.orderfood.model.Bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {
    RecyclerView rcv;
    BillAdapter adapter;
    Connection cons;
    dbConnection dbConnect;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        rcv = view.findViewById(R.id.rcv_bill_his);
        CreateRCV();

        return view;
    }

    private void CreateRCV(){
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new BillAdapter(SelectBillDB());
        adapter.notifyDataSetChanged();
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);
    }

    public List<Bill> SelectBillDB() {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From HOADON";
            List<Bill> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setmHD(rs.getInt("MA_HD"));
                    bill.setNgayHD(rs.getString("NGAYIN"));
                    bill.setTongTien(rs.getFloat("TONGTIEN"));
                    bill.setmLKM(rs.getInt("MA_KM"));
                    list.add(bill);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi");
        }
        return null;
    }
}

package com.example.orderfood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.adapter.CustomerAdapter;
import com.example.orderfood.adapter.StaffAdapter;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {
    CustomerAdapter adapter;
    RecyclerView rcv;
    List<Customer> customerList = new ArrayList<>();
    Connection cons;
    dbConnection dbConnect;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        rcv = view.findViewById(R.id.rcv_cus_info);
        customerList.clear();
        customerList.addAll(SelectStaffDB());
        setRCV();
        return view;
    }

    private void setRCV() {
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new CustomerAdapter(customerList);
//        totalNV.setText(String.valueOf(SelectStaffDB().size()));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);
    }

    public List<Customer> SelectStaffDB() {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From KHACHHANG";
            List<Customer> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId_kh(rs.getInt("ID_KH"));
                    customer.setHo(rs.getString("HO"));
                    customer.setTen(rs.getString("TEN"));
                    customer.setNgaysinh(rs.getString("NGAYSINH"));
                    customer.setPhai(rs.getInt("PHAI"));
                    customer.setSdt(rs.getString("SDT"));
                    customer.setEmail(rs.getString("EMAIL"));
                    customer.setDiachi(rs.getString("DIACHI"));
                    customer.setTrangthai(rs.getInt("TRANGTHAI"));
                    list.add(customer);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi getKH");
        }
        return null;
    }
}
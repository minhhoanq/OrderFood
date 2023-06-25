package com.example.orderfood.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.orderfood.AddProduct;
import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.adapter.ProductAdpater;
import com.example.orderfood.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductFragment extends Fragment {
    ProductAdpater adapter;
    RecyclerView rcv;
    Connection cons;
    dbConnection dbConnect;
    FloatingActionButton btnShowDialog;
    public static int ma_nv;
    List<Product> productList = new ArrayList<>();
    int indexPN;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        rcv = view.findViewById(R.id.rcv_product);
        btnShowDialog = view.findViewById(R.id.btn_dialog_product);
        productList.clear();
        productList.addAll(SelectProductDB());
        CreateRCV();
        showDialog();
        return view;
    }

    private void showDialog(){
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_product);

                EditText tensp = dialog.findViewById(R.id.edt_tsp_add);
//                ho.setText("Tran");
                EditText nsx = dialog.findViewById(R.id.edt_nsx_add);
//                ten.setText("Hoang");
                EditText hsd = dialog.findViewById(R.id.edt_hsd_add);
//                cmnd.setText("198271827182");
                EditText dongia = dialog.findViewById(R.id.edt_dg_add);
//                ngaysinh.setText("1999-09-18");
                EditText sl = dialog.findViewById(R.id.edt_sl_add);
//                sdt.setText("0987213786");
                EditText hinhanh = dialog.findViewById(R.id.edt_ha_add);
//                phai.setText("2");
                EditText mota = dialog.findViewById(R.id.edt_mota_add);
//                diachi.setText("Dong Nai");
                EditText mlsp = dialog.findViewById(R.id.edt_lsp_add);
//                email.setText("hoangtran@gmail.com");
                EditText mncc = dialog.findViewById(R.id.edt_ncc_add);
//                ps.setText("0123455");
                EditText mth = dialog.findViewById(R.id.edt_th_add);
//                email.setText("hoangtran@gmail.com");
                EditText mgg = dialog.findViewById(R.id.edt_gg_add);
//                ps.setText("0123455");

                ImageView btnAdd = dialog.findViewById(R.id.btn_add_product);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = tensp.getText().toString().trim();
                        String nsxsp = nsx.getText().toString().trim();
                        String hsdsp = hsd.getText().toString().trim();
                        float dongiasp = Float.valueOf(dongia.getText().toString().trim());
                        int slsp = Integer.parseInt(sl.getText().toString().trim());
                        String hinhanhsp = hinhanh.getText().toString().trim();
                        String motasp = mota.getText().toString().trim();
                        int mlspsp = Integer.parseInt(mlsp.getText().toString().trim());
                        int mnccsp = Integer.parseInt(mncc.getText().toString().trim());
                        int mthsp = Integer.parseInt(mth.getText().toString().trim());
                        int mggsp = Integer.parseInt(mgg.getText().toString().trim());
                        InsertPN();
                        getCountPN();

                        InsertCTPN(indexPN, SelectProductDB().size(), slsp);
                        InsertSP(ten, nsxsp, hsdsp, dongiasp, hinhanhsp, slsp, motasp, mlspsp, mnccsp, mthsp, mggsp);
                        productList.add(SelectProductDB().get(SelectProductDB().size() - 1));
                        adapter.notifyDataSetChanged();
                        rcv.setAdapter(adapter);

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void CreateRCV(){
        StaggeredGridLayoutManager stagger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ProductAdpater(productList);
        rcv.setLayoutManager(stagger);
        rcv.setAdapter(adapter);
    }

    public List<Product> SelectProductDB() {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From SANPHAM";
            List<Product> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setMaSP(rs.getInt("MA_SP"));
                    product.setTenSP(rs.getString("TENSP"));
                    product.setNxs(rs.getString("NSX"));
                    product.setHsd(rs.getString("HSD"));
                    product.setDonGia(rs.getFloat("DONGIA"));
                    product.setHinhAnh(rs.getString("HINHANH"));
                    product.setSoLuong(rs.getInt("SOLUONG"));
                    product.setMoTa(rs.getString("MOTA"));
                    product.setTinhTrang(rs.getString("TINHTRANG"));
                    product.setMaLSP(rs.getInt("MA_LSP"));
                    product.setMaNCC(rs.getInt("MA_NCC"));
                    product.setMaTH(rs.getInt("MA_TH"));
                    product.setMaGiamGia(rs.getInt("MA_GIAMGIA"));
                    list.add(product);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi SELECT");
        }
        return null;
    }

    private void InsertSP(String ten, String nsx, String hsd, float dongia, String hinhanh, int soluong, String mota, int mlsp, int mncc, int mth, int mgg) {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into SANPHAM(TENSP, NSX, HSD, DONGIA, HINHANH, SOLUONG, MOTA, TINHTRANG, MA_LSP, MA_NCC, MA_TH, MA_GIAMGIA)Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(ten));
            ps.setString(2, String.valueOf(nsx));
            ps.setString(3, String.valueOf(hsd));
            ps.setFloat(4, Float.parseFloat(String.valueOf(dongia)));
            ps.setString(5, String.valueOf(hinhanh));
            ps.setInt(6, Integer.parseInt(String.valueOf(soluong)));
            ps.setString(7, String.valueOf(mota));

            String c = "Hết hàng";
            if(soluong >= 1){
                c = "Còn hàng";
            }

            ps.setString(8, String.valueOf(c));
            ps.setInt(9, Integer.parseInt(String.valueOf(mlsp)));
            ps.setInt(10, Integer.parseInt(String.valueOf(mncc)));
            ps.setInt(11, Integer.parseInt(String.valueOf(mth)));
            ps.setInt(12, Integer.parseInt(String.valueOf(mgg)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi INSERT");
        }
    }

    private void InsertPN() {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into PHIEUNHAP(NGAYNHAP, ID_NV)Values(?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            Date d = new Date(System.currentTimeMillis());
            ps.setString(1, String.valueOf((d.getYear() + 1900) + "-" + (d.getMonth()+1) + "-" + d.getDate()));
            ps.setInt(2, Integer.parseInt(String.valueOf(ma_nv)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi INSERT");
        }
    }

    private void InsertCTPN(int mpn, int msp, int sl) {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into CT_PHIEUNHAP(MA_PN, MA_SP, SOLUONG)Values(?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(mpn)));
            ps.setInt(2, Integer.parseInt(String.valueOf(msp)));
            ps.setInt(3, Integer.parseInt(String.valueOf(sl)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi INSERT");
        }
    }

    private void getCountPN(){
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "SELECT TOP 1 MA_PN FROM PHIEUNHAP ORDER BY MA_PN DESC";
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    indexPN = rs.getInt("MA_PN");
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

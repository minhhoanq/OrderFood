package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.adapter.MyCartAdapter;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.GioHang;
import com.example.orderfood.model.Product;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private MyCartAdapter adapter;
    private ConstraintLayout btnCheck;
    private TextView tvtotalprice, tvtotalcart, tvmgg;
    private ImageView imgGHTrong;
    public static List<GioHang> listGHGlobal = new ArrayList<>();
    dbConnection dbConnect;
    Connection cons;
//    Customer customer;
    public int indexDDH = 0;
    public int indexHD = 0;
    public float sumTotal = 0;
    public static int id_kh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initView();
//        Intent i = getIntent();
//        Bundle bundle = i.getExtras();
//        customer = (Customer) bundle.get("info_cus");

        initList();

        calculaterCart(listGHGlobal);
        setViewTotal();
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDDH();
                InsertHD();
                getCountDDH();
                getCountHD();
                for(int i = 0; i < listGHGlobal.size(); i++){
                    GioHang gh = new GioHang();
                    gh = listGHGlobal.get(i);
                    float tiengoc = gh.getSoLuong() * gh.getTienGoc();
                    UpdateSP(gh.getMASP(), gh.getSoLuong());
                    Log.d("INDEX", indexDDH + " : " + indexHD);
                    InsertCTDDH(indexDDH, indexHD, gh.getMASP(), gh.getSoLuong(), tiengoc);
                }
                listGHGlobal.clear();
                initView();
                adapter.notifyDataSetChanged();
                rcv.setAdapter(adapter);
            }
        });

    }

    public void initView() {
        rcv = findViewById(R.id.rcv_cart);
        btnCheck = findViewById(R.id.btnCheck);
        tvtotalprice = findViewById(R.id.tv_total);
        tvtotalcart = findViewById(R.id.tv_total_cart);
        tvmgg = findViewById(R.id.tv_nhan_mgg);
        imgGHTrong = findViewById(R.id.layout_gh_trong);

        tvmgg.setText("Không áp dụng");

        if(!listGHGlobal.isEmpty()){
            imgGHTrong.setVisibility(View.INVISIBLE);
        }else {
            tvtotalprice.setText("");
            tvtotalcart.setText("");
            imgGHTrong.setVisibility(View.VISIBLE);
            btnCheck.setVisibility(View.INVISIBLE);
        }
    }

    private void initList(){
        LinearLayoutManager linearlayout = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        adapter = new MyCartAdapter(listGHGlobal, tvtotalprice, tvtotalcart, btnCheck, imgGHTrong, tvmgg);
        rcv.setLayoutManager(linearlayout);
        rcv.setAdapter(adapter);

    }

    public void calculaterCart(List<GioHang> list) {
        float sum = 0;
        for(int i = 0; i < list.size(); i++){
            sum += list.get(i).getTongTien();
        }
        sumTotal = sum;
    }

    private void setViewTotal(){
        if(sumTotal == 0) {
            tvtotalprice.setText(String.valueOf(0));
            tvtotalcart.setText(String.valueOf(0));
        }else{
            tvtotalprice.setText(String.valueOf(sumTotal));
            if (sumTotal < 50000) {
                tvmgg.setText("Không áp dụng.");
                tvtotalcart.setText(String.valueOf(sumTotal));
            } else if (sumTotal < 100000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 20000));
            } else  if (sumTotal < 200000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 30000));
            } else  if (sumTotal < 300000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 50000));
            } else if (sumTotal < 400000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 70000));
            } else if (sumTotal < 500000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 90000));
            } else if (sumTotal < 600000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 11000));
            } else if (sumTotal < 700000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 130000));
            } else if (sumTotal < 900000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 150000));
            } else if (sumTotal < 1000000) {
                tvtotalcart.setText(String.valueOf(sumTotal - 180000));
            } else {
                tvtotalcart.setText(String.valueOf(sumTotal - 200000));
            }
        }

        InsertKM();
    }

    private void InsertHD() {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into HOADON(NGAYIN)Values(?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            Date d = new Date(System.currentTimeMillis());
            ps.setString(1,  String.valueOf((d.getYear() + 1900) + "-" + (d.getMonth()+1) + "-" + d.getDate()));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi INSERT HD");
        }
    }

    private void UpdateSP(int msp, int sl) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update SANPHAM set SOLUONG = ?, TINHTRANG = ? where MA_SP = " + msp;

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            Product product = getProduct(msp);
            int updateSL = product.getSoLuong() - sl;
            ps.setInt(1, Integer.parseInt(String.valueOf(updateSL)));
            String tt = "Hết hàng";
            if(updateSL >= 1) {
                tt = "Còn hàng";
            }
            ps.setString(2, tt);

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi Update sp");
        }
    }


    private Product getProduct(int msp) {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From SANPHAM Where MA_SP="+msp;
            Product product = new Product();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {

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
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return product;
        }catch (Exception e){
            Log.d("III", "lỗi get SP");
        }
        return null;
    }

    private void InsertDDH() {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into DONDATHANG(NGAYDH, PHIVC, TINHTRANG, ID_KH)Values(?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

//            ps.setInt(1, index);

            Date d = new Date(System.currentTimeMillis());
            ps.setString(1, String.valueOf((d.getYear() + 1900) + "-" + (d.getMonth()+1) + "-" + d.getDate()));
            ps.setFloat(2, Float.parseFloat(String.valueOf(15000)));
            ps.setInt(3, Integer.parseInt(String.valueOf(0)));
            ps.setInt(4, Integer.parseInt(String.valueOf(id_kh)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
        }
    }

    private void getCountDDH(){
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "SELECT TOP 1 MA_DDH FROM DONDATHANG ORDER BY MA_DDH DESC";
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    indexDDH = rs.getInt("MA_DDH");
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi get count MA_DDH");
        }
    }

    private void InsertCTDDH(int indexDDH, int indexHD, int msp, int sl, float tiengoc) {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into CT_DONDATHANG(MA_DDH, MA_SP, SOLUONG, TIENHANG, MA_HD)Values(?, ?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(indexDDH)));
            ps.setInt(2, Integer.parseInt(String.valueOf(msp)));
            ps.setInt(3, Integer.parseInt(String.valueOf(sl)));
            ps.setFloat(4, Float.parseFloat(String.valueOf(tiengoc)));
            ps.setInt(5, Integer.parseInt(String.valueOf(indexHD)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("CT_DDH", "lỗi get CT_DDH");
        }
    }

    private void InsertKM() {
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
            String sql = "SELECT * FROM KHUYENMAI Where MA_KM = " + km;
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

    private void getCountHD(){
        try {
            //SELECT TOP 1 ID_NV FROM NHANVIEN ORDER BY ID_NV DESC
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "SELECT TOP 1 MA_HD FROM HOADON ORDER BY MA_HD DESC";
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    indexHD = rs.getInt("MA_HD");
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi get HD");
        }
    }
}
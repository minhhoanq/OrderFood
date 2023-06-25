package com.example.orderfood.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.adapter.DDHAdapter;
import com.example.orderfood.adapter.StaffAdapter;
import com.example.orderfood.model.Bill;
import com.example.orderfood.model.DonDatHang;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rcv;
    private DDHAdapter adapter;
    private TextView tvDoanhthu, tvTongdh, tvTenNv;
    dbConnection dbConnect;
    Connection cons;
    public static String emailGlobal;
    public Staff st;
    private ImageView btnFmStaff;

    public static HomeFragment getInstance(Staff staff){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_staff", staff);
        homeFragment.setArguments(bundle);

        return homeFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvDoanhthu = view.findViewById(R.id.tv_doanh_thu);
        tvTongdh = view.findViewById(R.id.tv_tong_dh);
        tvTenNv = view.findViewById(R.id.tv_ten_nv);

        st = SelectStaffDB(emailGlobal);
        ProductFragment.ma_nv = st.getId_NV();

        tvTenNv.setText("Nhân viên : " + st.getTenNV());

        tvDoanhthu.setText(String.valueOf(setViewDoanhThu()));
        tvTongdh.setText(String.valueOf(SelectBillDB().size()));
        rcv = view.findViewById(R.id.rcv_ddh);
        CreateRCV();
        return view;
    }

    private float setViewDoanhThu() {
        float sum = 0;
        for(int i = 0; i < SelectBillDB().size(); i++){
            sum += Float.valueOf(SelectBillDB().get(i).getTongTien());
        }
        return sum;
    }

    public void CreateRCV(){
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new DDHAdapter(getListDDh(), st);
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);;
    }

    private List<DonDatHang> getListDDh() {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From DONDATHANG";
            List<DonDatHang> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    DonDatHang ddh = new DonDatHang();
                    ddh.setMA_DDH(rs.getInt("MA_DDH"));
                    ddh.setNgayDH(rs.getString("NGAYDH"));
                    ddh.setID_KH(rs.getInt("ID_KH"));
                    ddh.setID_NV(rs.getInt("ID_NV"));
                    ddh.setTinhTrang(rs.getInt("TINHTRANG"));
                    list.add(ddh);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi getNV");
        }
        return null;
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

    public Staff SelectStaffDB(String email) {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From NHANVIEN where EMAIL = " + "'" + email + "'";
            Staff staff = new Staff();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    staff.setId_NV(rs.getInt("ID_NV"));
                    staff.setHoNV(rs.getString("HO"));
                    staff.setTenNV(rs.getString("TEN"));
                    staff.setNgaySinh(rs.getString("NGAYSINH"));
                    staff.setNgaySinh(rs.getString("SDT"));
                    staff.setDiaChi(rs.getString("DIACHI"));
                    staff.setEmail(rs.getString("EMAIL"));
                    staff.setCmnd(rs.getString("CMND"));
                    staff.setGioiTinh(rs.getInt("PHAI"));
                    staff.setTrangthai(rs.getInt("TRANGTHAI"));
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return staff;
        }catch (Exception e){
            Log.d("III", "lỗi getNV");
        }
        return null;
    }

}

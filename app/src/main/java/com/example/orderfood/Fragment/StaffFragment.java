package com.example.orderfood.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.adapter.StaffAdapter;
import com.example.orderfood.model.Staff;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends Fragment {
    Connection cons;
    dbConnection dbConnect;
    StaffAdapter adapter;
    RecyclerView rcv;
    TextView totalNV;
    FloatingActionButton btnShowDialog;
    List<Staff> listStaff = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        rcv = view.findViewById(R.id.rcv_staff_info);
        totalNV = view.findViewById(R.id.tv_total_nv);
        btnShowDialog = view.findViewById(R.id.btn_show_dialog);
        listStaff.clear();
        listStaff.addAll(SelectStaffDB());
        totalNV.setText(String.valueOf(listStaff.size()));

        //func CRUD
        CreateRCV();
        showDialog();
        return view;
    }

    public void CreateRCV(){
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new StaffAdapter(listStaff);
        totalNV.setText(String.valueOf(SelectStaffDB().size()));
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);
    }

    private void showDialog(){
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_form_staff);

                EditText ho = dialog.findViewById(R.id.edt_add_ho);
//                ho.setText("Tran");
                EditText ten = dialog.findViewById(R.id.edt_add_ten);
//                ten.setText("Hoang");
                EditText cmnd = dialog.findViewById(R.id.edt_add_cmnd);
//                cmnd.setText("198271827182");
                EditText ngaysinh = dialog.findViewById(R.id.edt_add_ns);
//                ngaysinh.setText("1999-09-18");
                EditText sdt = dialog.findViewById(R.id.edt_add_sdt);
//                sdt.setText("0987213786");
                EditText phai = dialog.findViewById(R.id.edt_add_phai);
//                phai.setText("2");
                EditText diachi = dialog.findViewById(R.id.edt_add_dc);
//                diachi.setText("Dong Nai");
                EditText email = dialog.findViewById(R.id.edt_add_email);
//                email.setText("hoangtran@gmail.com");
                EditText ps = dialog.findViewById(R.id.edt_add_ps);
//                ps.setText("0123455");

                ImageView btnAdd = dialog.findViewById(R.id.btn_add_staff);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hoSt = ho.getText().toString().trim();
                        String tenSt = ten.getText().toString().trim();
                        String cmndSt = cmnd.getText().toString().trim();
                        String ngaysinhSt = ngaysinh.getText().toString().trim();
                        String sdtSt = sdt.getText().toString().trim();
                        int phaiSt = Integer.parseInt(phai.getText().toString().trim());
                        String diachiSt = diachi.getText().toString().trim();
                        String eSt = email.getText().toString().trim();
                        String pSt = ps.getText().toString().trim();
                        Log.d("HHH", hoSt + " " + tenSt + " " + cmndSt + " " + ngaysinhSt + " " + sdtSt + " " + phaiSt + " " + diachiSt + " " + eSt + " " + pSt);
//
//                        SelectStaffDB().add(new Staff(hoSt, tenSt, sdtSt, eSt, diachiSt, ngaysinhSt, cmndSt, phaiSt, pSt));
                        //String ho, String ten, String cmnd, String ngaysinh, int phai, String sdt, String email, String diachi, String pw)
                        InsertACC(eSt, pSt);
                        InsertNV(hoSt, tenSt, cmndSt, ngaysinhSt, phaiSt, sdtSt, eSt, diachiSt);

                        listStaff.add(SelectStaffDB().get(SelectStaffDB().size() - 1));

                        adapter.notifyDataSetChanged();
//                        totalNV.setText(listStaff.size());
                        rcv.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public List<Staff> SelectStaffDB() {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From NHANVIEN";
            List<Staff> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId_NV(rs.getInt("ID_NV"));
                    staff.setHoNV(rs.getString("HO"));
                    staff.setTenNV(rs.getString("TEN"));
                    staff.setCmnd(rs.getString("CMND"));
                    staff.setNgaySinh(rs.getString("NGAYSINH"));
                    staff.setGioiTinh(rs.getInt("PHAI"));
                    staff.setSdt(rs.getString("SDT"));
                    staff.setEmail(rs.getString("EMAIL"));
                    staff.setDiaChi(rs.getString("DIACHI"));
                    staff.setTrangthai(rs.getInt("TRANGTHAI"));
                    list.add(staff);
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

    private void InsertNV(String honv, String tennv, String cmndnv, String ngaysinhnv, int phainv, String sdtnv, String emailnv, String diachinv) {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into NHANVIEN(HO, TEN, NGAYSINH, SDT, DIACHI, EMAIL, CMND, PHAI, TRANGTHAI)Values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(honv));
            ps.setString(2, String.valueOf(tennv));
            ps.setString(3, String.valueOf(ngaysinhnv));
            ps.setString(4, String.valueOf(sdtnv));
            ps.setString(5, String.valueOf(diachinv));
            ps.setString(6, String.valueOf(emailnv));
            ps.setString(7, String.valueOf(cmndnv));
            ps.setInt(8, phainv);
            ps.setInt(9, 0);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();

        } catch (Exception ex) {
            Log.d("III", "Lỗi NV");
        }
    }

    private void InsertACC(String email, String pass) {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into TAIKHOAN(EMAIL, PASSWORD, ID_CHUCVU)Values(?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(email));
            ps.setString(2, String.valueOf(pass));
            ps.setInt(3, 2);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "Lỗi ACC");
        }
    }
}

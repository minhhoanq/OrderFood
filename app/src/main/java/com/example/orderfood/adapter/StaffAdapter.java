package com.example.orderfood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    private List<Staff> listStaff;
    private dbConnection dbConnect;
    Connection cons;

    public StaffAdapter(List<Staff> listStaff) {
        this.listStaff = listStaff;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_info_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        Staff staff = listStaff.get(position);
        int id = position;

        if(staff == null) return;

        holder.tvTen.setText(staff.getHoNV() + " " + staff.getTenNV());

        int gt = staff.getGioiTinh();

        if(gt == 2) {
            holder.tvGioiTinh.setText("Nam");
        }else{
            holder.tvGioiTinh.setText("Nữ");
        }


        holder.tvChucVu.setText("Nhân viên");


        if(staff.getTrangthai() == 0) {
            holder.swCheck.setChecked(true);
            holder.tvTrangthai.setText("Đang hoạt động");
            holder.textCv.setTextColor(Color.parseColor("#2196F3"));
            holder.textTt.setTextColor(Color.parseColor("#2196F3"));
            holder.imgDotChange.setImageResource(R.drawable.cs_dot);
        }else if(staff.getTrangthai() == 1){
            holder.swCheck.setChecked(false);
            holder.tvTrangthai.setText("Ngừng hoạt động");
            holder.textCv.setTextColor(Color.GRAY);
            holder.textTt.setTextColor(Color.GRAY);
            holder.imgDotChange.setImageResource(R.drawable.cs_dot_red);
        }

        holder.btnShowStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WWW", "vô đây");
                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_form_staff);

                EditText ho = dialog.findViewById(R.id.edt_add_ho);
                ho.setText(String.valueOf(staff.getHoNV()));
                EditText ten = dialog.findViewById(R.id.edt_add_ten);
                ten.setText(String.valueOf(staff.getTenNV()));
                EditText cmnd = dialog.findViewById(R.id.edt_add_cmnd);
                cmnd.setText(String.valueOf(staff.getCmnd()));
                EditText ngaysinh = dialog.findViewById(R.id.edt_add_ns);
                ngaysinh.setText(String.valueOf(staff.getNgaySinh()));
                EditText sdt = dialog.findViewById(R.id.edt_add_sdt);
                sdt.setText(String.valueOf(staff.getSdt()));
                EditText phai = dialog.findViewById(R.id.edt_add_phai);
                phai.setText(String.valueOf(staff.getGioiTinh()));
                EditText diachi = dialog.findViewById(R.id.edt_add_dc);
                diachi.setText(String.valueOf(staff.getDiaChi()));
                EditText email = dialog.findViewById(R.id.edt_add_email);
                email.setText(String.valueOf(staff.getEmail()));
                EditText ps = dialog.findViewById(R.id.edt_add_ps);

                ps.setText("0123455");

                ImageView btnAdd = dialog.findViewById(R.id.btn_add_staff);
                TextView tvAdd = dialog.findViewById(R.id.tv_add_st);
                tvAdd.setText("Cập nhật");
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

                        Update(staff, hoSt, tenSt, cmndSt, ngaysinhSt, phaiSt, sdtSt, eSt, diachiSt);
                        listStaff.set(id, SelectStaffDB().get(id));
                        notifyItemChanged(id);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.swCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = listStaff.get(position).getTrangthai() == 0;
                if(isChecked) {
                    listStaff.get(position).setTrangthai(1);
                    holder.tvTrangthai.setText("Ngừng hoạt động");
                    holder.textCv.setTextColor(Color.GRAY);
                    holder.textTt.setTextColor(Color.GRAY);
                    holder.imgDotChange.setImageResource(R.drawable.cs_dot_red);
                    UpdateTT(staff, staff.getTrangthai());
                    holder.swCheck.setChecked(false);
                }else {
                    listStaff.get(position).setTrangthai(0);
                    holder.tvTrangthai.setText("Đang hoạt động");
                    holder.textCv.setTextColor(Color.parseColor("#2196F3"));
                    holder.textTt.setTextColor(Color.parseColor("#2196F3"));
                    holder.imgDotChange.setImageResource(R.drawable.cs_dot);
                    UpdateTT(staff, staff.getTrangthai());
                    holder.swCheck.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listStaff != null) return listStaff.size();
        return 0;
    }

    public class StaffViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTen, tvGioiTinh, tvChucVu, tvTrangthai, textCv, textTt;
        private ConstraintLayout btnShowStaff;
        private SwitchCompat swCheck;
        private ImageView imgDotChange;
        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tv_name_staff);
            tvGioiTinh = itemView.findViewById(R.id.tv_gt_staff);
            tvChucVu = itemView.findViewById(R.id.tv_cv_staff);
            tvTrangthai = itemView.findViewById(R.id.tv_tt_staff);
            swCheck = itemView.findViewById(R.id.sw_check_tt);
            imgDotChange = itemView.findViewById(R.id.img_dot_change);
            textCv = itemView.findViewById(R.id.tv_text_cv);
            textTt = itemView.findViewById(R.id.tv_text_tt);

            btnShowStaff = itemView.findViewById(R.id.btn_show_info_staff);
        }
    }

    private void UpdateTT(Staff staff, int tt) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update NHANVIEN set TRANGTHAI = ? where EMAIL = " + "'" + staff.getEmail() + "'";

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(tt)));

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi");
        }
    }

    private void Update(Staff staff, String honv, String tennv, String cmndnv, String ngaysinhnv, int phainv, String sdtnv, String emailnv, String diachinv) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update NHANVIEN set HO = ?, TEN = ?, NGAYSINH = ?, PHAI = ?, SDT = ?, EMAIL = ?, DIACHI = ?, CMND = ?, TRANGTHAI = ? where EMAIL = " + "'" + staff.getEmail() + "'";

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(honv));
            ps.setString(2, String.valueOf(tennv));
            ps.setString(3, String.valueOf(ngaysinhnv));
            ps.setInt(4, Integer.parseInt(String.valueOf(phainv)));
            ps.setString(5, String.valueOf(sdtnv));
            ps.setString(6, String.valueOf(emailnv));
            ps.setString(7, String.valueOf(diachinv));
            ps.setString(8, String.valueOf(cmndnv));
            ps.setInt(9, Integer.parseInt(String.valueOf(0)));

            ps.executeUpdate();
//            UpdateTaiKhoan(customer);
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi");
        }
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
}

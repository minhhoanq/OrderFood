package com.example.orderfood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.model.Bill;
import com.example.orderfood.model.ChiTietDDH;
import com.example.orderfood.model.DonDatHang;
import com.example.orderfood.model.Product;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DDHAdapter extends RecyclerView.Adapter<DDHAdapter.DDHViewHolder> {
    private List<DonDatHang> listDDH;
    private Staff staff;
    private int indexDDH;
    Connection cons;
    dbConnection dbConnect;

    public DDHAdapter(List<DonDatHang> listDDH, Staff staff) {
        this.listDDH = listDDH;
        this.staff = staff;
    }

    @NonNull
    @Override
    public DDHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_ddh, parent, false);
        return new DDHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DDHViewHolder holder, int position) {
        DonDatHang ddh = listDDH.get(position);

        if(ddh == null) return;

        holder.maddh.setText("Mã đơn đặt hàng : " + ddh.getMA_DDH());
        holder.ngaydh.setText(ddh.getNgayDH());
        holder.mnv.setText(String.valueOf(ddh.getID_NV()));
        holder.mkh.setText(String.valueOf(ddh.getID_KH()));

        int tt = ddh.getTinhTrang();

        Log.d("TTT", "tt = " + staff.getTenNV());
        if(tt == 0) {
            holder.btnDuyet.setVisibility(View.VISIBLE);
        }else {
            holder.btnDuyet.setVisibility(View.INVISIBLE);
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_ct_ddh);

                TextView masp = dialog.findViewById(R.id.tv_msp_ddh);
                TextView slsp = dialog.findViewById(R.id.tv_sl_ddh);
                TextView dongiasp = dialog.findViewById(R.id.tv_dg_ddh);

                int mddh = ddh.getMA_DDH();;
                List<ChiTietDDH> chiTietDDHList = SelectStaffDB(mddh);


                String m = "";
                String s = "";
                String d = "";
                for (int i = 0; i < chiTietDDHList.size(); i++) {
                    m += String.valueOf(chiTietDDHList.get(i).getMa_sp()) + "\n";
                    s += String.valueOf(chiTietDDHList.get(i).getSoluong()) + "\n";
                    d += String.valueOf(chiTietDDHList.get(i).getDongia()) + "\n";
                }
                masp.setText(m);
                slsp.setText(s);
                dongiasp.setText(d);

                dialog.show();
            }
        });

        holder.btnDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mddh = ddh.getMA_DDH();
                List<ChiTietDDH> chiTietDDHList = SelectStaffDB(mddh);
                float sum = 0;
                for (int i = 0; i < chiTietDDHList.size(); i++) {
                    sum += (chiTietDDHList.get(i).getDongia());
                }

                int mhd = chiTietDDHList.get(0).getMa_hd();
                int mgg = setGetMGG(sum);
                float tongFinal = totalFinal(sum);

                holder.mnv.setText(String.valueOf(staff.getId_NV()));
                UpdateDDH(ddh.getMA_DDH());
                holder.btnDuyet.setVisibility(View.INVISIBLE);
                UpdateHD(mhd, mgg, tongFinal);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listDDH != null) return listDDH.size();
        return 0;
    }

    public class DDHViewHolder extends RecyclerView.ViewHolder {
        TextView maddh, ngaydh, mnv, mkh;
        ConstraintLayout layoutItem;
        ImageView btnDuyet;
        public DDHViewHolder(@NonNull View itemView) {
            super(itemView);
            maddh = itemView.findViewById(R.id.tv_name_ddh);
            ngaydh = itemView.findViewById(R.id.tv_ngay_dh);
            mnv = itemView.findViewById(R.id.tv_ma_nv_dh);
            mkh = itemView.findViewById(R.id.tv_ma_kh_dh);
            layoutItem = itemView.findViewById(R.id.btn_show_info_ddh);
            btnDuyet = itemView.findViewById(R.id.btn_duyet_ddh);
        }
    }

    public List<ChiTietDDH> SelectStaffDB(int m_ddh) {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From CT_DONDATHANG Where MA_DDH = " + m_ddh;
            List<ChiTietDDH> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    ChiTietDDH ctddh = new ChiTietDDH();
                    ctddh.setMa_ddh(rs.getInt("MA_DDH"));
                    ctddh.setMa_sp(rs.getInt("MA_SP"));
                    ctddh.setSoluong(rs.getInt("SOLUONG"));
                    ctddh.setDongia(rs.getFloat("TIENHANG"));
                    ctddh.setMa_hd(rs.getInt("MA_HD"));

                    list.add(ctddh);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        }catch (Exception e){
            Log.d("III", "lỗi getCT_DDH");
        }
        return null;
    }

    private void UpdateHD(int mhd, int mgg, float tongtien) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update HOADON set MA_KM = ?, TONGTIEN = ? where MA_HD = " + mhd;

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(mgg)));
            ps.setFloat(2, Float.parseFloat(String.valueOf(tongtien)));

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi Update sp");
        }
    }

    private void UpdateDDH(int mddh) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update DONDATHANG set TINHTRANG = ?, ID_NV = ? where MA_DDH = " + mddh;

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, Integer.parseInt(String.valueOf(1)));
            ps.setInt(2, Integer.parseInt(String.valueOf(staff.getId_NV())));

            ps.executeUpdate();
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi Update sp");
        }
    }

    private int setGetMGG(float sumTotal){
        if(sumTotal == 0) {
            return 0;
        }else{
            if (sumTotal < 50000) {
                return 0;
            } else if (sumTotal < 100000) {
                return 1;
            } else  if (sumTotal < 200000) {
                return 2;
            } else  if (sumTotal < 300000) {
                return 3;
            } else if (sumTotal < 400000) {
                return 4;
            } else if (sumTotal < 500000) {
                return 5;
            } else if (sumTotal < 600000) {
                return 6;
            } else if (sumTotal < 700000) {
                return 7;
            } else if (sumTotal < 900000) {
                return 8;
            } else if (sumTotal < 1000000) {
                return 9;
            } else {
                return 10;
            }
        }
    }

    private float totalFinal(float sumTotal){
        if(sumTotal == 0) {
            return 0;
        }else{
            if (sumTotal < 50000) {
                return sumTotal;
            } else if (sumTotal < 100000) {
                return sumTotal - 20000;
            } else  if (sumTotal < 200000) {
                return sumTotal - 30000;
            } else  if (sumTotal < 300000) {
                return sumTotal - 50000;
            } else if (sumTotal < 400000) {
                return sumTotal - 70000;
            } else if (sumTotal < 500000) {
                return sumTotal - 90000;
            } else if (sumTotal < 600000) {
                return sumTotal - 110000;
            } else if (sumTotal < 700000) {
                return sumTotal - 130000;
            } else if (sumTotal < 900000) {
                return sumTotal - 150000;
            } else if (sumTotal < 1000000) {
                return sumTotal - 180000;
            } else {
                return sumTotal - 200000;
            }
        }
    }
}

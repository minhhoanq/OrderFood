package com.example.orderfood.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.R;
import com.example.orderfood.ShowProductActivity;
import com.example.orderfood.model.Product;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class ProductAdpater extends RecyclerView.Adapter<ProductAdpater.ProductViewHolder> {
    private List<Product> listProduct;
    dbConnection dbConnect;

    public ProductAdpater(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);

        if(product == null) return;

        holder.tenProduct.setText(product.getTenSP());
//        holder.slProduct.setText(String.valueOf(product.getSoLuong()));
//        holder.ttProduct.setText(String.valueOf(product.getTinhTrang()));

        Glide.with(holder.imgProduct.getContext())
                .load(product.getHinhAnh())
                .into(holder.imgProduct);


        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                c.startActivity(new Intent(c, ShowProductActivity.class)
                        .putExtra("info_product", product)
                );
            }
        });

        holder.layoutProduct.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Context c = v.getContext();
                Dialog dialog = new Dialog(c);
                dialog.setContentView(R.layout.dialog_product);

                TextView ten = dialog.findViewById(R.id.edt_tsp_add);
                TextView nsx = dialog.findViewById(R.id.edt_nsx_add);
                TextView hsd = dialog.findViewById(R.id.edt_hsd_add);
                TextView dongia = dialog.findViewById(R.id.edt_dg_add);
                TextView sl = dialog.findViewById(R.id.edt_sl_add);
                TextView ha = dialog.findViewById(R.id.edt_ha_add);
                TextView mota = dialog.findViewById(R.id.edt_mota_add);
                TextView lsp = dialog.findViewById(R.id.edt_lsp_add);
                TextView ncc = dialog.findViewById(R.id.edt_ncc_add);
                TextView th = dialog.findViewById(R.id.edt_th_add);
                TextView gg = dialog.findViewById(R.id.edt_gg_add);
                TextView tvBtn = dialog.findViewById(R.id.tv_add_pr);
                tvBtn.setText("Cập nhật");
                ImageView btnUpdate = dialog.findViewById(R.id.btn_add_product);

                ten.setText(product.getTenSP());
                nsx.setText(product.getNxs());
                hsd.setText(product.getHsd());
                dongia.setText(String.valueOf(product.getDonGia()));
                sl.setText(String.valueOf(product.getSoLuong()));
                ha.setText(product.getHinhAnh());
                mota.setText(product.getMoTa());
                lsp.setText(String.valueOf(product.getMaLSP()));
                ncc.setText(String.valueOf(product.getMaNCC()));
                th.setText(String.valueOf(product.getMaTH()));
                gg.setText(String.valueOf(product.getMaGiamGia()));

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
        //Update(Product product, String ten, String nsx, String hsd, float dongia, String mota, int lsp, int ncc, int th, int gg, String hinhanh, int sl)
                        Update(product, String.valueOf(ten.getText())
                                , String.valueOf(nsx.getText())
                                , String.valueOf(hsd.getText())
                                , Float.parseFloat(String.valueOf(dongia.getText()))
                                , String.valueOf(mota.getText())
                                , Integer.parseInt(String.valueOf(lsp.getText()))
                                , Integer.parseInt(String.valueOf(ncc.getText()))
                                , Integer.parseInt(String.valueOf(th.getText()))
                                , Integer.parseInt(String.valueOf(gg.getText()))
                                , String.valueOf(ha.getText())
                                , Integer.parseInt(String.valueOf(sl.getText())));
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listProduct != null) return listProduct.size();
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tenProduct, tvadd, ttProduct;
        CardView layoutProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tenProduct = itemView.findViewById(R.id.tv_ten_product);
//            slProduct = itemView.findViewById(R.id.tv_so_luong_product);
            layoutProduct = itemView.findViewById(R.id.layout_item_product);
        }
    }

    private void Update(Product product, String ten, String nsx, String hsd, float dongia, String mota, int lsp, int ncc, int th, int gg, String hinhanh, int sl) {
        try {
            Connection cons = (Connection) dbConnect.connectionClass();
            String sql = "Update SANPHAM set TENSP = ?, NSX = ?, HSD = ?, DONGIA = ?, MOTA = ?, TINHTRANG = ?, MA_LSP = ?, MA_NCC = ?, MA_TH = ?, MA_GIAMGIA = ?, HINHANH = ?, SOLUONG = ? where MA_SP = " + product.getMaSP();

            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(ten));
            ps.setString(2, String.valueOf(nsx));
            ps.setString(3, String.valueOf(hsd));
            ps.setFloat(4, Float.parseFloat(String.valueOf(dongia)));
            ps.setString(5, String.valueOf(mota));

            String tt = "";
            if(sl <= 0) {
                tt = "Hết hàng";
            }else {
                tt = "Còn hàng";
            }

            ps.setString(6, String.valueOf(tt));
            ps.setInt(7, Integer.parseInt(String.valueOf(lsp)));
            ps.setInt(8, Integer.parseInt(String.valueOf(ncc)));
            ps.setInt(9, Integer.parseInt(String.valueOf(th)));
            ps.setInt(10, Integer.parseInt(String.valueOf(gg)));
            ps.setString(11, String.valueOf(hinhanh));
            ps.setInt(12, Integer.parseInt(String.valueOf(sl)));

            ps.executeUpdate();
//            UpdateTaiKhoan(customer);
            ps.close();
            cons.close();
        } catch (Exception ex) {
            Log.d("III", "lỗi update sp");
        }
    }
}

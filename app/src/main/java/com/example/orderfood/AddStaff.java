//package com.example.orderfood;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.orderfood.Connection.dbConnection;
//import com.example.orderfood.Fragment.StaffFragment;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class AddStaff extends AppCompatActivity {
//
//    private EditText ho, ten, cmnd, ngaysinh, phai, sdt, email, diachi, pass;
//    private ImageView btn_add;
//    private TextView notify;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_staff);
//        Init();
//
//        addNVDB();
//    }
//
//    private void addNVDB() {
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InsertNV();
//
//                cmnd.setText("");
//                ho.setText("");
//                ten.setText("");
//                ngaysinh.setText("");
//                phai.setText("");
//                sdt.setText("");
//                email.setText("");
//                diachi.setText("");
//                pass.setText("");
////                new Timer().schedule(new TimerTask() {
////                    @Override
////                    public void run() {
////
////                    }
////                }, 2000);
//
//            }
//        });
//    }
//
//    private void InsertNV() {
//        try {
//            Connection cons = (Connection) dbConnection.connectionClass();
//            String sql = "Insert into NHANVIEN(CMND, HO, TEN, NGAYSINH, PHAI, SDT, EMAIL, DIACHI)Values(?, ?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement ps;
//            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            ps.setInt(1, Integer.parseInt(String.valueOf(cmnd.getText())));
//            ps.setString(2, String.valueOf(ho.getText()));
//            ps.setString(3, String.valueOf(ten.getText()));
//            ps.setString(4, String.valueOf(ngaysinh.getText()));
//            ps.setInt(5, Integer.parseInt(String.valueOf(phai.getText())));
//            ps.setString(6, String.valueOf(sdt.getText()));
//            ps.setString(7, String.valueOf(email.getText()));
//            ps.setString(8, String.valueOf(diachi.getText()));
//
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//
//            ps.close();
//            rs.close();
//            cons.close();
//            notify.setText("Thành công!");
//            notify.setTextColor(getResources().getColor(R.color.green));
//            InsertACC();
//        } catch (Exception ex) {
//            notify.setText("Thất bại, hãy thử lại sau!");
//            notify.setTextColor(getResources().getColor(R.color.red));
//        }
//    }
//
//    private void InsertACC() {
//        try {
//            Connection cons = (Connection) dbConnection.connectionClass();
//            String sql = "Insert into TAIKHOAN(EMAIL, PASSWORD, ID_CHUCVU)Values(?, ?, ?)";
//            PreparedStatement ps;
//            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//
//            ps.setString(1, String.valueOf(email.getText()));
//            ps.setString(2, String.valueOf(pass.getText()));
//            ps.setInt(3, 2);
//
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//
//            ps.close();
//            rs.close();
//            cons.close();
//        } catch (Exception ex) {
//            //
//        }
//    }
//
//    public void Init(){
//        ho = findViewById(R.id.edt_ho);
//        ten = findViewById(R.id.edt_ten);
//        cmnd = findViewById(R.id.edt_cmnd);
//        ngaysinh = findViewById(R.id.edt_ngay_sinh);
//        phai = findViewById(R.id.edt_phai);
//        sdt = findViewById(R.id.edt_sdt);
//        email = findViewById(R.id.edt_email);
//        diachi = findViewById(R.id.edt_dia_chi);
//        pass = findViewById(R.id.edt_pass);
//        btn_add = findViewById(R.id.btn_add_db);
//        notify = findViewById(R.id.tv_notify);
//    }
//}
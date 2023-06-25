package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.Fragment.HomeFragment;
import com.example.orderfood.model.ACCOUNT;
import com.example.orderfood.model.Customer;
import com.example.orderfood.model.Staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText email, pass;
    private TextView msgEmail, msgPass, msgCheckLogin;

    private ConstraintLayout btnLogin;
    private ConstraintLayout labelRegister;

    Connection cons;
    dbConnection dbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();

        setOnClickEvent();
    }

    private void Init(){
        email           = findViewById(R.id.edt_e_lg);
        pass            = findViewById(R.id.edt_p_lg);
        btnLogin        = findViewById(R.id.btn_login);
        labelRegister   = findViewById(R.id.label_register);

        msgEmail        = findViewById(R.id.tv_email_msg);
        msgEmail.setVisibility(View.INVISIBLE);

        msgPass         = findViewById(R.id.tv_ps_msg);
        msgPass.setVisibility(View.INVISIBLE);

        msgCheckLogin = findViewById(R.id.tv_check_login);
        msgCheckLogin.setVisibility(View.INVISIBLE);

        //public static final int TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        //
        email.setText("quyet@gmail.com");
        pass.setText("quyet123456");

        dbConnect = new dbConnection();
    }

    private void setOnClickEvent(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = checkLogin();
                if(c == 1) {
                    List<ACCOUNT> acs = getListAccount();
                    if(acs == null) {
                        Log.d("SSS", "Null");
                    }
                    int ttcs = -1;
                    int ttst = -1;
                    Customer cs = SelectCusDB(String.valueOf(email.getText()));
                    if(cs != null){
                        ttcs = cs.getTrangthai();
                    }
                    if(SelectStaffDB(String.valueOf(email.getText())) != null){
                        ttst = SelectStaffDB(String.valueOf(email.getText())).getTrangthai();
                    }
                    Log.d("TTT", "TT = " + ttcs + " " + ttst);
                    if(ttcs == 0 && ttst == 0) {
                        for(ACCOUNT ac : acs){
                            Log.d("SSS", email.getText() + " " + pass.getText());
                            String emailV = String.valueOf(email.getText());
                            boolean emailValid = ac.getEmail().contentEquals(email.getText());

                            int id = ac.getId_cv();
                            int check = 0;
                            if(id == 1){
                                check = 1;
                            }else if(id == 2){
                                check = 2;
                            }else {
                                check = 3;
                            }

                            Log.d("SSS", emailValid + " " + ac.getPassword() + " " + pass.getText());
                            if(emailValid){
                                if(ac.getPassword().equals(String.valueOf(pass.getText()))){
                                    msgCheckLogin.setVisibility(View.INVISIBLE);
                                    if(check == 1 || check == 2) {
                                        startActivity(new Intent(LoginActivity.this, ManagerActivity.class)
                                                .putExtra("key_cv", check)
                                                .putExtra("key_staff", SelectStaffDB(String.valueOf(email.getText()))));
                                        return;
                                    }else {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                                .putExtra("key_email", emailV));
                                        return;
                                    }
                                }else{
                                    msgCheckLogin.setText("Vui lòng kiểm tra lại mật khẩu.");
                                    msgCheckLogin.setVisibility(View.VISIBLE);
                                    return;
                                }
                            }else {
                                msgCheckLogin.setText("Tài khoản không tồn tại trong hệ thống.");
                                msgCheckLogin.setVisibility(View.VISIBLE);
                            }
                        }
                    }else {
                        msgCheckLogin.setText("Tài khoản này đã bị vô hiệu hóa.");
                        msgCheckLogin.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        labelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public List<ACCOUNT> getListAccount () {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From TAIKHOAN";
            List<ACCOUNT> list = new ArrayList<>();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    ACCOUNT ac = new ACCOUNT();
                    ac.setEmail(rs.getString("EMAIL"));
                    ac.setPassword(rs.getString("PASSWORD"));
                    ac.setId_cv(rs.getInt("ID_CHUCVU"));
                    list.add(ac);
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return list;
        } catch (Exception ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.d("III", "lỗi");
        }
        return null;
    }

    public int checkLogin(){
        if(isEmail() == 1 && isPassword() == 1) {
            msgEmail.setVisibility(View.INVISIBLE);
            msgPass.setVisibility(View.INVISIBLE);
            return 1;
        }
        if(isEmail() != 1){
            msgEmail.setVisibility(View.VISIBLE);
            msgCheckLogin.setVisibility(View.INVISIBLE);
        }else {
            msgEmail.setVisibility(View.INVISIBLE);
        }

        if(isPassword() != 1){
            msgPass.setVisibility(View.VISIBLE);
            msgCheckLogin.setVisibility(View.INVISIBLE);
        }else {
            msgPass.setVisibility(View.INVISIBLE);
        }
        return 0;
    }

    public int isEmail(){
        String emailInput = email.getText().toString().trim();

        if(emailInput.isEmpty()){
            return 0;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return 0;
        } else {
            return 1;
        }
    }

    private int isPassword(){
        String pwInput = pass.getText().toString().trim();
        if(pwInput.isEmpty()){
            return  0;
        }else {
            if(pwInput.length() < 3){
                return  0;
            }else{
                return  1;
            }
        }
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

    public Customer SelectCusDB(String email) {
        try {
            cons = dbConnect.connectionClass();
            String sql = "Select * From KHACHHANG where EMAIL = " + "'" + email + "'";
            Customer customer = new Customer();
            Statement stmt = cons.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs != null) {
                while (rs.next()) {
                    customer.setTrangthai(rs.getInt("TRANGTHAI"));
                }
            }else {
                Log.d("III", "vô");
            }
            rs.close();
            stmt.close();
            cons.close();
            return customer;
        }catch (Exception e){
            Log.d("III", "lỗi getNV");
        }
        return null;
    }
}
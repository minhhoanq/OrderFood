package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.Connection.dbConnection;
import com.example.orderfood.model.ACCOUNT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, pass, passcf, ho, ten, ns, phai, sdt, diachi;
    private ConstraintLayout btnRs;
    private dbConnection dbConnect;
    private TextView msgEmail, msgPass, msgPassCf, msgFName, msgLName, msgNs, msgPhai, msgSdt, msgDiachi, msgCheckRs;

    Connection cons;
    PreparedStatement stmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Init();
        //Click event
        setOnClickEvent();
    }

    private void Init(){
        email = findViewById(R.id.edt_e_rs);
        pass = findViewById(R.id.edt_p_rs);
        passcf = findViewById(R.id.edt_p_rs_cf);
        ho = findViewById(R.id.edt_f_name);
        ten = findViewById(R.id.edt_l_name);
        ns = findViewById(R.id.edt_ns_rs);
        phai = findViewById(R.id.edt_phai_rs);
        sdt = findViewById(R.id.edt_sdt_rs);
        diachi = findViewById(R.id.edt_dc_rs);

        btnRs = findViewById(R.id.btn_rs);

        msgFName = findViewById(R.id.tv_f_name);
        msgFName.setVisibility(View.INVISIBLE);

        msgLName = findViewById(R.id.tv_l_name);
        msgLName.setVisibility(View.INVISIBLE);

        msgEmail = findViewById(R.id.tv_is_e);
        msgEmail.setVisibility(View.INVISIBLE);

        msgPass = findViewById(R.id.tv_is_p);
        msgPass.setVisibility(View.INVISIBLE);

        msgPassCf = findViewById(R.id.tv_is_p_cf);
        msgPassCf.setVisibility(View.INVISIBLE);

        msgNs = findViewById(R.id.tv_ns_msg);
        msgNs.setVisibility(View.INVISIBLE);

        msgPhai = findViewById(R.id.tv_phai_msg);
        msgPhai.setVisibility(View.INVISIBLE);

        msgSdt = findViewById(R.id.tv_sdt_msg);
        msgSdt.setVisibility(View.INVISIBLE);

        msgDiachi = findViewById(R.id.tv_dc_msg);
        msgDiachi.setVisibility(View.INVISIBLE);

        msgCheckRs = findViewById(R.id.tv_check_rs);
        msgCheckRs.setVisibility(View.INVISIBLE);

        dbConnect = new dbConnection();

    }

    private void setOnClickEvent () {
        btnRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Vailidation(email.getText().toString(),
                        pass.getText().toString(),
                        passcf.getText().toString(),
                        ho.getText().toString(),
                        ten.getText().toString(),
                        ns.getText().toString(),
                        phai.getText().toString(),
                        sdt.getText().toString(),
                        diachi.getText().toString());
                if(c == 1){
                    List<ACCOUNT> list = getListAccount();
                    int check = 0;
                    for(int i = 0; i < list.size(); i++){
                        if(list.get(i).getEmail().equals(String.valueOf(email.getText()))){
                            check = 1;
                            break;
                        }
                    }
                    if(check == 0){
                        new RegisterActivity.Registeruser().execute("");
                    }
                    if (check == 1){
                        msgCheckRs.setText("Email đã tồn tại.");
                        msgCheckRs.setTextColor(Color.RED);
                        msgCheckRs.setVisibility(View.VISIBLE);
                    }
                }
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

    public class Registeruser extends AsyncTask<String, String, String>{

        String z = "";
        Boolean inSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            ho.setText("");
            ten.setText("");
            email.setText("");
            pass.setText("");
            passcf.setText("");
            ns.setText("");
            phai.setText("");
            sdt.setText("");
            diachi.setText("");
            msgCheckRs.setText("Đăng ký thành công.");
            msgCheckRs.setTextColor(Color.GREEN);
            msgCheckRs.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                cons = dbConnect.connectionClass();
                if(cons == null) {
                    z = "Check your Internet Connection";
                }else{

                    String sqli = "Insert into TAIKHOAN(EMAIL, PASSWORD, ID_CHUCVU)Values(?, ?, ?)";
                    PreparedStatement ps;
                    ps = cons.prepareStatement(sqli, PreparedStatement.RETURN_GENERATED_KEYS);

                    ps.setString(1, String.valueOf(email.getText()));
                    ps.setString(2, String.valueOf(pass.getText()));
                    ps.setInt(3, 3);

                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    InsertKH();
                    ps.close();
                    rs.close();
                    cons.close();
                }
            }catch (Exception e) {
                inSuccess = false;
                z = e.getMessage();
                msgCheckRs.setText("Đăng ký thất bại.");
                msgCheckRs.setTextColor(Color.RED);
                msgCheckRs.setVisibility(View.VISIBLE);
            }
            return z;
        }
    }

    private void InsertKH() {
        try {
            Connection cons = (Connection) dbConnection.connectionClass();
            String sql = "Insert into KHACHHANG(HO, TEN, NGAYSINH, PHAI, SDT, DIACHI, EMAIL, TRANGTHAI)Values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps;
            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, String.valueOf(ho.getText()));
            ps.setString(2, String.valueOf(ten.getText()));
            ps.setString(3, String.valueOf(ns.getText()));
            ps.setInt(4, Integer.parseInt(String.valueOf(phai.getText())));
            ps.setString(5, String.valueOf(sdt.getText()));
            ps.setString(6, String.valueOf(diachi.getText()));
            ps.setString(7, String.valueOf(email.getText()));
            ps.setInt(8, Integer.parseInt(String.valueOf(0)));

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            ps.close();
            rs.close();
            cons.close();
        } catch (Exception ex) {
        }
    }

    private int Vailidation (String e, String p, String pcf, String fname, String lname, String ns, String gt, String sdt, String dc){
        if(isEmail(e) == 1
                && isPassword(p) == 1
                    && isPasswordCF(pcf) == 1
                        && isFName(fname) == 1
                            && isLName(lname) == 1
                                && isNgaySinh(ns) == 1
                                    && isPhai(gt) == 1
                                        && isSdt(sdt) == 1
                                            && isDiachi(dc) == 1){
            msgEmail.setVisibility(View.INVISIBLE);
            msgPass.setVisibility(View.INVISIBLE);
            msgPassCf.setVisibility(View.INVISIBLE);
            msgFName.setVisibility(View.INVISIBLE);
            msgLName.setVisibility(View.INVISIBLE);
            msgNs.setVisibility(View.INVISIBLE);
            msgPhai.setVisibility(View.INVISIBLE);
            msgSdt.setVisibility(View.INVISIBLE);
            msgDiachi.setVisibility(View.INVISIBLE);
            return 1;
        }
        if(isEmail(e) != 1 ) {
            msgEmail.setVisibility(View.VISIBLE);
        }else {
            msgEmail.setVisibility(View.INVISIBLE);
        }

        if (isPassword(p) != 1){
            msgPass.setVisibility(View.VISIBLE);
        }else {
            msgPass.setVisibility(View.INVISIBLE);
        }

        if (isPasswordCF(pcf) != 1) {
            msgPassCf.setVisibility(View.VISIBLE);
        }else {
            msgPassCf.setVisibility(View.INVISIBLE);
        }

        if(isFName(fname) != 1){
            msgFName.setVisibility(View.VISIBLE);
        }else {
            msgFName.setVisibility(View.INVISIBLE);
        }

        if(isLName(lname) != 1){
            msgLName.setVisibility(View.VISIBLE);
        }else {
            msgLName.setVisibility(View.INVISIBLE);
        }

        if(isNgaySinh(ns) != 1){
            msgNs.setVisibility(View.VISIBLE);
        }else {
            msgNs.setVisibility(View.INVISIBLE);
        }

        if(isPhai(gt) != 1){
            msgPhai.setVisibility(View.VISIBLE);
        }else {
            msgPhai.setVisibility(View.INVISIBLE);
        }

        if(isSdt(sdt) != 1){
            msgSdt.setVisibility(View.VISIBLE);
        }else {
            msgSdt.setVisibility(View.INVISIBLE);
        }

        if(isDiachi(dc) != 1){
            msgDiachi.setVisibility(View.VISIBLE);
        }else {
            msgDiachi.setVisibility(View.INVISIBLE);
        }

        return 0;
    }

    private int isFName(String fname){
        if(fname.isEmpty()){
            return 0;
        }
        return 1;
    }

    private int isLName(String lname){
        if(lname.isEmpty()){
            return 0;
        }
        return 1;
    }

    private int isEmail(String e){
        String emailInput = e.trim();
        if(emailInput.isEmpty()){
            return 0;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return 0;
        } else {
            return 1;
        }
    }

    private int isPassword(String p){
        int checkP = 0;
        if(p.isEmpty()){
            checkP = 0;
        }else {
            if(p.length() < 3){
                checkP = 0;
            }else{
                checkP = 1;
            }
        }
        return checkP;
    }

    private int isPasswordCF(String pcf){
        int checkPcf = 0;
        if(pcf.isEmpty()){
            checkPcf = 0;
        }else {
            if (pcf.equals(pass.getText().toString())) {
                checkPcf = 1;
            }
        }
        return checkPcf;
    }

    private int isNgaySinh(String ns){
        if(ns.isEmpty()){
            return 0;
        }
        return 1;
    }
    private int isPhai(String gt){
        if(gt.isEmpty()){
            return 0;
        }
        return 1;
    }
    private int isSdt(String sdt){
        if(sdt.isEmpty()){
            return 0;
        }
        return 1;
    }
    private int isDiachi(String dc){
        if(dc.isEmpty()){
            return 0;
        }
        return 1;
    }
}
package com.example.orderfood.Connection;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    @SuppressLint("NewApi")
    public static Connection connectionClass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            //IP: 192.168.1.4 là IP mạng đang dùng, dùng Port: 1433
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://192.168.1.4:1433;databaseName=update_cnpm_3;integratedSecurity=true;user=sa;password=123;";
            connection = DriverManager.getConnection(connectionURL);
        }catch (Exception e) {
            Log.e("SQL Connect Error: ", e.getMessage());
        }
        return connection;
    }
}

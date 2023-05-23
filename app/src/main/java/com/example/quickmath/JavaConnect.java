package com.example.quickmath;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class JavaConnect {

    public static Connection connectDb(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://192.168.88.54:25566/players?useSSL=false";
            //String url="jdbc:mysql://potnar.duckdns.org:25566/players?useSSL=false";
            String user = "korisnik";
            String pass="zavrsni";
            Connection conn = DriverManager.getConnection(url,user,pass);
            Log.d("SQL","Uspijeh");
            return conn;

        }catch(Exception e){
            Log.d("SQL",e.toString());
        }
        return null;
    }

}

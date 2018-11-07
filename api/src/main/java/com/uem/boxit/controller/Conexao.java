package com.uem.boxit.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection conector() {
        String username = "root";
        String password = "password";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/boxit_api?useSSL=false";
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

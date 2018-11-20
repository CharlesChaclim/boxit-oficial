package com.uem.boxit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {


    private static Connection connection;

    public static Connection getConnection() {
        String datasource = "jdbc:mysql://localhost:3306/boxit_api?createDatabaseIfNotExist=true&useSSL=false&useTimezone=true&serverTimezone=GMT-3&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "";
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(datasource, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

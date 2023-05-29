/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/cashbo?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return connection;
    }
}

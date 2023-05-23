/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
}

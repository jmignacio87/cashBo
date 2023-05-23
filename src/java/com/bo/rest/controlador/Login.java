/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.bo.rest.modelos.Token;

/**
 *
 * @author aarauco2608
 */
public class Login {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public String singIn(String source, String body) {
        JsonObject result = new JsonObject();
        Integer code = 0;
        String message = "Success";

        try {
            Token tokenBody = this.gson.fromJson(body, Token.class);

            if (!verifyCredentials(tokenBody)) {
                throw new Exception();
            }

            String token = TokenUtils.generateJwt(body);

            if (token != null) {
                JsonObject data = new JsonObject();
                data.addProperty("authToken", token);
                data.addProperty("refreshToken", token);

                result.add("data", data);
            } else {
                code = -1;
                message = "Error";
            }

            result.addProperty("code", code);
            result.addProperty("message", message);

        } catch (Exception e) {
            code = -1;
            message = "Error";
        }

        result.addProperty("code", code);
        result.addProperty("message", message);

        return result.toString();
    }

    private boolean verifyCredentials(Token token) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select date(now())");

            // Execute SQL statements using the statement object
            // ...
            // Close the statement and connection when done
            statement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

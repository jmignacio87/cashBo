/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.TokenQuery;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.bo.rest.modelos.TokenModel;

/**
 *
 * @author aarauco2608
 */
public class LoginController {
    
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();
    
    public String singIn(String source, String body) {
        JsonObject result = new JsonObject();
        Integer code = 0;
        String message = "Success";
        
        try {
            TokenModel tokenBody = this.gson.fromJson(body, TokenModel.class);
            String credentials = this.getCredentials(source, tokenBody);
            
            if (credentials == null) {
                throw new Exception();
            }
            
            String token = TokenUtils.generateJwt(credentials);
            
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
    
    private String getCredentials(String source, TokenModel token) {
        try {
            
            Statement statement = connection.createStatement();
            String query = TokenQuery.getQueryToken(source, token);
            
            ResultSet result = statement.executeQuery(query);
            
            if (result.next()) {
                token.setPassword(null);
                token.setType(source);
                return this.gson.toJson(token);
            }
            
            return null;
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
}

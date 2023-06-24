/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.ConfigurationModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author aarauco2608
 */
public class ConfigurationController {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public String getConfiguration(String authorization, ConfigurationModel model) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            }

            return this.getResultConfiguration(model).toString();
        } catch (Exception e) {
            return null;
        }
    }

    private JsonObject getResultConfiguration(ConfigurationModel model) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = PartnerQuery.getQueryConfiguration();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                code = 0;
                message = "success";
                
                JsonObject data = new JsonObject();
                data.addProperty("partnerid", result.getString("partnerid"));
                data.addProperty("transactiontype", result.getString("transactiontype"));
                data.addProperty("searchradius", result.getString("searchradius"));
                data.addProperty("withdrawalCurrency", result.getString("withdrawalCurrency"));
                data.addProperty("minwithdrawalamountpertransaction", result.getString("minwithdrawalamountpertransaction"));
                data.addProperty("maxwithdrawalamountpertransaction", result.getString("maxwithdrawalamountpertransaction"));
                data.addProperty("cust_acct_debited", result.getString("cust_acct_debited"));

                response.add("data", data);
            }
        } catch (Exception e) {
            code = -1;
            message = "Error";
        }

        response.addProperty("code", code);
        response.addProperty("message", message);

        return response;
    }

}

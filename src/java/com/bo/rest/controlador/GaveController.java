/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import java.sql.Connection;
import com.bo.rest.modelos.GaveModel;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author aarauco2608
 */
public class GaveController {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public String getGaveRequest(String authorization, String body) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            }

            GaveModel gave = this.gson.fromJson(body, GaveModel.class);

            return this.getResultQuery(gave).toString();
        } catch (Exception e) {
            return null;
        }
    }

    private JsonObject getResultQuery(GaveModel gave) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryGave(gave.getSocash_txn_id(), gave.getPurchase_amount());

            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                code = 0;
                message = "Success";
            } else {
                throw new Exception();
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

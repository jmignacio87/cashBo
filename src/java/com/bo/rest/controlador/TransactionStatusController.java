/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.TransactionStatusModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author aarauco2608
 */
public class TransactionStatusController {

    Connection connection = DBConnection.getConnection();

    public String getTransactionStatus(String authorization, TransactionStatusModel model) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                throw new Exception();
            }

            return this.getResultTransactionStatus(model).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public JsonObject getResultTransactionStatus(TransactionStatusModel model) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = PartnerQuery.getQueryTransactionStatus(model.getSocash_txn_id());
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                JsonObject data = new JsonObject();
                data.addProperty("requestId", "");
                data.addProperty("partnerid", result.getString("partnerid"));
                data.addProperty("socash_txn_id", result.getString("socash_txn_id"));
                data.addProperty("bank_txn_id", result.getString("bank_txn_id"));
                data.addProperty("transactionStatus", result.getString("transactionStatus"));

                response.add("data", data);
            }

            code = 0;
            message = "success";
        } catch (Exception e) {
            code = -1;
            message = "Error";
        }

        response.addProperty("code", code);
        response.addProperty("message", message);

        return response;
    }

}

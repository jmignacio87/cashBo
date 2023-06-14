/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.modelos.Token;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author aarauco2608
 */
public class TransactionDetails {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public String getTransactionDetail(String authorization, String body) {
        try {

            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            Token token = this.gson.fromJson(jsonObject, Token.class);
            TransactionDetails transactionDetailsBody = this.gson.fromJson(body, TransactionDetails.class);

            return this.getResponseTransactionDetail(token).toString();
        } catch (Exception e) {
            return null;
        }
    }

    private JsonObject getResponseTransactionDetail(Token token) {
        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryTransactionsDetails(token.getDeviceId());

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                JsonObject jsonResponse = new JsonObject();

                jsonResponse.addProperty("partnerid", result.getString("partnerid"));
                jsonResponse.addProperty("cashpoint_id", result.getInt("cashpoint_id"));
                jsonResponse.addProperty("cashpoint_name", result.getString("cashpoint_name"));
                jsonResponse.addProperty("amount", result.getInt("amount"));
                jsonResponse.addProperty("socash_txn_id", result.getInt("socash_txn_id"));
                jsonResponse.addProperty("bank_txn_id", result.getInt("bank_txn_id"));
                jsonResponse.addProperty("transactionStatus", result.getInt("transactionStatus"));
                jsonResponse.addProperty("transactionCreationTime", result.getString("transactionCreationTime"));
                jsonResponse.addProperty("uniqueCustomerIdentifier", result.getString("uniqueCustomerIdentifier"));
                jsonResponse.addProperty("withdrawalCurrency", result.getString("withdrawalCurrency"));
                jsonResponse.addProperty("queue_number", result.getInt("queue_number"));
                jsonResponse.addProperty("cashpoint_latitude", result.getString("cashpoint_latitude"));
                jsonResponse.addProperty("cashpoint_longitude", result.getString("cashpoint_longitude"));

                return jsonResponse;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
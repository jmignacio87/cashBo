/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.modelos.Token;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.bo.rest.modelos.Transaction;
import com.bo.rest.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author aarauco2608
 */
public class Transactions {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public Transactions() {
    }

    public String getPendingTransactions(String authorization) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                throw new Exception();
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            Token token = this.gson.fromJson(jsonObject, Token.class);

            return this.gson.toJson(token);
        } catch (Exception e) {
            return "";
        }
    }

    public String getTransactionDetail(String authorization, String body) {
        try {

            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();

            Token token = this.gson.fromJson(jsonObject, Token.class);

            LinkedList<Transaction> listTransactionPendings = this.getListTransactionPendings(token);

            JsonObject response = new JsonObject();

            response.addProperty("code", 0);
            response.addProperty("message", "success");

            JsonObject jsonTransaction = new JsonObject();
            jsonTransaction.addProperty("transactions", this.gson.toJson(listTransactionPendings));

            response.add("data", jsonTransaction);

            return response.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private LinkedList<Transaction> getListTransactionPendings(Token token) {
        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryTransactionsPending(token.getDeviceId());

            ResultSet result = statement.executeQuery(query);

            LinkedList<Transaction> list = new LinkedList<Transaction>();
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setSocash_txn_id(result.getString("socash_txn_id"));

                list.add(transaction);
            }

            return list;
        } catch (Exception e) {
            return null;
        }
    }
}

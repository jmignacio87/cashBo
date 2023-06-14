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
import com.google.gson.JsonArray;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
            
            ArrayList<Transaction> listTransactionPendings = this.getListTransactionPendings(token);
            
            JsonObject response = new JsonObject();
            
            response.addProperty("code", 0);
            response.addProperty("message", "success");
            
            JsonArray jsonListTransaction = new Gson().toJsonTree(listTransactionPendings).getAsJsonArray();
            JsonObject jsonTransaction = new JsonObject();
            jsonTransaction.add("transactions", jsonListTransaction);
            
            response.add("data", jsonTransaction);
            
            return response.toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    private ArrayList<Transaction> getListTransactionPendings(Token token) {
        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryTransactionsPending(token.getDeviceId());
            
            ResultSet result = statement.executeQuery(query);
            
            ArrayList<Transaction> list = new ArrayList<>();
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setSocash_txn_id(result.getString("socash_txn_id"));
                transaction.setQueue_number(result.getInt("queue_number"));
                
                list.add(transaction);
            }
            
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}

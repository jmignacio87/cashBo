/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.bo.rest.modelos.TransactionModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.LogsUtils;
import com.google.gson.JsonArray;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class TransactionsController {

    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public TransactionsController() {
        this.logger = LogsUtils.getLogger("TRANSACTION");
    }

    public String getPendingTransactions(String authorization, String bank_id) {
        try {
            this.logger.debug("Transaction Method - Parametros | token: {} | bank_id", authorization, bank_id);
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
                this.logger.debug("Token Valido");
            } else {
                this.logger.debug("Token Expirado");
                throw new Exception();
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            TokenModel token = this.gson.fromJson(jsonObject, TokenModel.class);

            ArrayList<TransactionModel> listTransactionPendings = this.getListTransactionPendings(token, bank_id);

            JsonObject response = new JsonObject();

            response.addProperty("code", 0);
            response.addProperty("message", "success");

            JsonArray jsonListTransaction = new Gson().toJsonTree(listTransactionPendings).getAsJsonArray();
            JsonObject jsonTransaction = new JsonObject();
            jsonTransaction.add("transactions", jsonListTransaction);

            response.add("data", jsonTransaction);

            return response.toString();
        } catch (Exception e) {
            this.logger.debug("Exception Transaction: {}", e.toString());
            return null;
        }
    }

    private ArrayList<TransactionModel> getListTransactionPendings(TokenModel token, String bank_id) {
        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryTransactionsPending(token.getDeviceId(), bank_id);
            this.logger.debug("Query Transaction: {}", query);

            ResultSet result = statement.executeQuery(query);

            ArrayList<TransactionModel> list = new ArrayList<>();
            while (result.next()) {
                TransactionModel transaction = new TransactionModel();
                transaction.setSocash_txn_id(result.getString("socash_txn_id"));
                transaction.setQueue_number(result.getInt("queue_number"));

                list.add(transaction);
            }

            return list;
        } catch (Exception e) {
            this.logger.debug("Exception Query Transaction: {}", e.toString());
            return null;
        }
    }
}

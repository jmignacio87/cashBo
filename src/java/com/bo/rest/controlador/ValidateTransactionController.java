/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.modelos.ValidateTransactionModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.LogsUtils;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class ValidateTransactionController {

    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public ValidateTransactionController() {
        this.logger = LogsUtils.getLogger("VALIDATE_TRANSACTION");
    }

    public String getValidateTransactionRequest(String authorization, String body) {
        try {
            this.logger.debug("Validate Transaction Method - Parametros | token: {} | body: {}", authorization, body);
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
                this.logger.debug("Token Valido");
            } else {
                this.logger.debug("Token Expirado`");
                throw new Exception();
            }

            ValidateTransactionModel validationTransaction = this.gson.fromJson(body, ValidateTransactionModel.class);
            return this.getResultQuery(validationTransaction).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Validate Transaction: {}", e.toString());
            return null;
        }
    }

    private JsonObject getResultQuery(ValidateTransactionModel validateTransaction) {

        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryValidateTransaction(validateTransaction.getSocash_txn_id(), validateTransaction.getRandom_code());
            this.logger.debug("Query Validate Transaction: {}", query);

            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                String estado = result.getString("ESTADO");
                String partnerid = result.getString("partnerid");
                if (estado.equals("nuevo")) {
                    query = CashpointQuery.getQueryUpdateTransaction(validateTransaction.getRequestId(), validateTransaction.getSocash_txn_id(), validateTransaction.getRandom_code());

                    statement.execute(query);
                }

                code = 0;
                message = "Success";

                JsonObject data = new JsonObject();
                data.addProperty("requestId", validateTransaction.getRequestId());
                data.addProperty("partnerid", partnerid);
                data.addProperty("bank_txn_id", validateTransaction.getBank_txn_id());
                response.add("data", data);
            } else {
                this.logger.debug("Query Validate Transaction | No hay resultados");
                throw new Exception();
            }
        } catch (Exception e) {
            code = -1;
            message = "Error";
            this.logger.debug("Exception Query Validate Transaction: {}", e.toString());
        }

        response.addProperty("code", code);
        response.addProperty("message", message);
        return response;
    }
}

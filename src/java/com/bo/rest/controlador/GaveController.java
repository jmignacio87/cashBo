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
import com.bo.rest.utils.LogsUtils;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class GaveController {

    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public GaveController() {
        this.logger = LogsUtils.getLogger("GAVE");
    }

    public String getGaveRequest(String authorization, String body) {
        try {
            this.logger.debug("Gave Parametros: token: {} | body: {}", authorization, body);
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                this.logger.debug("Token valido");
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                this.logger.debug("Token expirado");
                throw new Exception();
            }

            GaveModel gave = this.gson.fromJson(body, GaveModel.class);

            return this.getResultQuery(gave).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Gave: {}", e.toString());
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

            this.logger.debug("Query Gave: {}", query);

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
            this.logger.debug("Exception Query Gave: {}", e.toString());
        }

        response.addProperty("code", code);
        response.addProperty("message", message);
        return response;
    }
}

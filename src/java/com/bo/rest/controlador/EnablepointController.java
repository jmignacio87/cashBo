/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.CashpointQuery;
import com.bo.rest.modelos.EnablepointModel;
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
 * @author IGNACIO
 */
public class EnablepointController {
    
    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public EnablepointController() {
        this.logger = LogsUtils.getLogger("ENABLEPOINT");
    }
    
    public String getEnablepointRequest(String authorization, String body) {
        try {
            this.logger.debug("Enablepoint Parametros: token: {} | body: {} ", authorization, body);
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                this.logger.debug("Token valido");
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                this.logger.debug("Token expirado");
                throw new Exception();
            }

            EnablepointModel enablepoint = this.gson.fromJson(body, EnablepointModel.class);

            return this.getResultQuery(enablepoint).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Enablepoint: {}", e.toString());
            return null;
        }
    }
    
    private JsonObject getResultQuery(EnablepointModel enablepoint) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = CashpointQuery.getQueryUpdateEnablepoint(enablepoint.getCashpint_id(), enablepoint.getEstado());

            this.logger.debug("Query Enablepoint: {}", query);

            //ResultSet result = 
                    statement.execute(query);

           // if (result.next()) {
                code = 0;
                message = "Success";
            //} else {
              //  throw new Exception();
            //}
        } catch (Exception e) {
            code = -1;
            message = "Error";
            this.logger.debug("Exception Query Enablepoint: {}", e.toString());
        }

        response.addProperty("code", code);
        response.addProperty("message", message);
        return response;
    }
    
}

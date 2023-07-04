/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.ConfigurationModel;
import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.LogsUtils;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class ConfigurationController {

    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public ConfigurationController() {
        this.logger = LogsUtils.getLogger("CONFIGURATION");
    }

    public String getConfiguration(String authorization, ConfigurationModel model) {
        try {
            this.logger.debug("Get Configuration Parametros: token: {} | configuration model: {}", authorization, this.gson.toJson(model));
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                this.logger.debug("Token Valido");
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                this.logger.debug("Token expirado");
                throw new Exception();
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            TokenModel token = this.gson.fromJson(jsonObject, TokenModel.class);

            return this.getResultConfiguration(model, token).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Configuration: {}", e.toString());
            return null;
        }
    }

    private JsonObject getResultConfiguration(ConfigurationModel model, TokenModel token) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = PartnerQuery.getQueryConfiguration(token.getDeviceId());
            this.logger.debug("Query Configuration: {}", query);
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
            this.logger.debug("Exception Query Configuration: {}", e.toString());
        }

        response.addProperty("code", code);
        response.addProperty("message", message);

        return response;
    }

}

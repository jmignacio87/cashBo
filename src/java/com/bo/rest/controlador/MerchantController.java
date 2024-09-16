/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.MerchantModel;
import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.LogsUtils;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class MerchantController {
    
    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();
    
    public MerchantController() {
        this.logger = LogsUtils.getLogger("MERCHANT");
    }
    
    public String getMerchant(String authorization, String body) {
        try {
            this.logger.debug("Merchant Parametros: token: {} | body: {}", authorization, body);
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
            
            JsonObject jsonMerchant = new JsonParser().parse(body).getAsJsonObject();
            MerchantModel model = this.gson.fromJson(jsonMerchant, MerchantModel.class);
            
            return this.getResponseMerchant(model, token).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Gave: {}", e.toString());
            return null;
        }
    }
    
    private JsonObject getResponseMerchant(MerchantModel model, TokenModel token) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";
        
        try {
            String queryStoreProcedure = "{call request_merchant(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = null;
            cs = connection.prepareCall(queryStoreProcedure);
            cs.setString(1, model.getTransactionSource());
            cs.setString(2, token.getDeviceId());
            cs.setInt(3, model.getAmount());
            cs.setString(4, model.getCashpoint_id());
            cs.setString(5, model.getPosition_lat());
            cs.setString(6, model.getPosition_lng());
            cs.setString(7, model.getAddress());
            cs.setString(8, token.getUniqueCustomerIdentifier());
            cs.registerOutParameter(9, Types.VARCHAR);
            
            this.logger.debug("Query SP request_merchant: {}", queryStoreProcedure);
            this.logger.debug("Query SP request_merchant parametros: {} | {} | {}| {} | {} | {} | {} | {}",
                    model.getTransactionSource(),
                    token.getDeviceId(),
                    model.getAmount(),
                    model.getCashpoint_id(),
                    model.getPosition_lat(),
                    model.getPosition_lng(),
                    model.getAddress(),
                    token.getUniqueCustomerIdentifier()
            );
            
            cs.execute();
            
            String cashpoint_id_out = cs.getString(9);
            
            Statement statement = connection.createStatement();
            String queryMerchant = PartnerQuery.getQueryMerchant(cashpoint_id_out);
            this.logger.debug("Query Merchant: {}", queryMerchant);
            ResultSet result = statement.executeQuery(queryMerchant);
            
            if (result.next()) {
                JsonObject data = new JsonObject();
                
                data.addProperty("partnerid", result.getString("partnerid"));
                data.addProperty("cashpoint_id", result.getString("cashpoint_id"));
                data.addProperty("cashpoint_name", result.getString("cashpoint_name"));
                data.addProperty("cashpoint_phone", result.getString("cashpoint_phone"));
                data.addProperty("cashpoint_address", result.getString("cashpoint_address"));
                data.addProperty("cashpoint_latitude", result.getString("cashpoint_latitude"));
                data.addProperty("cashpoint_longitude", result.getString("cashpoint_longitude"));
                data.addProperty("cashpoint_description", result.getString("cashpoint_description"));
                data.addProperty("amount", result.getString("amount"));
                data.addProperty("withdrawalCurrency", result.getString("withdrawalCurrency"));
                data.addProperty("socash_txn_id", result.getString("socash_txn_id"));
                data.addProperty("transactionStatus", result.getString("transactionStatus"));
                data.addProperty("transactiontype", result.getString("transactiontype"));
                data.addProperty("queue_number", result.getString("queue_number"));
                data.addProperty("transactionCreationTime", result.getString("transactionCreationTime"));
                data.addProperty("random_code", result.getString("random_code"));
                data.addProperty("requestactiveperiod", "15"); //agregamos el parametro para control de fortaleza
                
                code = 0;
                message = "Success";
                response.add("data", data);
            }
            
        } catch (Exception e) {
            code = -1;
            message = "Error";
            this.logger.debug("Exception Query Merchant: {}", e.toString());
        }
        
        response.addProperty("code", code);
        response.addProperty("message", message);
        return response;
    }
    
}

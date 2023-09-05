/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.CancelModel;
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
import static java.sql.Types.NULL;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class CancelController {
    
    private Logger logger;
    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();
    
    public CancelController() {
        this.logger = LogsUtils.getLogger("CANCEL");
    }
    
    public String getCancel(String authorization, String body) {
        try {
            this.logger.debug("Cancel Parametros: token: {} | body: {}", authorization, body);
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
            
            JsonObject jsonCancel = new JsonParser().parse(body).getAsJsonObject();
            CancelModel model = this.gson.fromJson(jsonCancel, CancelModel.class);
            
            return this.getResponseCancel(model, token).toString();
        } catch (Exception e) {
            this.logger.debug("Exception Cacel: {}", e.toString());
            return null;
        }
    }
    
    private JsonObject getResponseCancel(CancelModel cancel, TokenModel token) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";
        
         try {
            Statement statement = connection.createStatement();
            String query = PartnerQuery.getQueryCancel(cancel.getSocash_txn_id(), cancel.getTransactionSource());
            
            this.logger.debug("Query Cancel: {}", query);

            int result = statement.executeUpdate(query);

            if (result > 0) {
                code = 0;
                message = "Success";
                JsonObject data = new JsonObject();
                
                
                data.addProperty("requestId", NULL);
                data.addProperty("partnerid", token.getDeviceId());
                data.addProperty("ipAddress", NULL);
                data.addProperty("socash_txn_id", cancel.getSocash_txn_id() );                
                
                response.add("data", data);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            code = -1;
            message = "Error al cancelar codigo, posiblemente el codigo ya este expirado";
            this.logger.debug("Exception update cancel: {}", e.toString());
        }
        
        response.addProperty("code", code);
        response.addProperty("message", message);       
        return response;
    }
    
}

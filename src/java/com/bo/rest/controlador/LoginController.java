/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.TokenQuery;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.LogsUtils;
import com.bo.rest.utils.TypeUtils;
import org.slf4j.Logger;

/**
 *
 * @author aarauco2608
 */
public class LoginController {

    /*
    Steps
    1. Get Credentials
    2. SQL funcion
    
     */
    private Logger logger;

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public LoginController() {
        this.logger = LogsUtils.getLogger("LOGIN");
    }

    public String singIn(String source, String body) {
        this.logger.debug("SignIn Parametros: source -> {} | body -> {}", source, body);

        JsonObject result = new JsonObject();
        Integer code = 0;
        String message = "Success";

        try {
            TokenModel tokenBody = this.gson.fromJson(body, TokenModel.class);
            String credentials = this.getCredentials(source, tokenBody);

            if (credentials == null) {
                this.logger.debug("No existen las credenciales");
                throw new Exception();
            }

            String token = TokenUtils.generateJwt(credentials);

            if (token != null) {
                JsonObject data = new JsonObject();
                data.addProperty("authToken", token);
                data.addProperty("refreshToken", token);

                result.add("data", data);
                this.logger.debug("Token generado: {}", data.toString());
            } else {
                code = -1;
                message = "Error";
                this.logger.debug("Error al generar el token");
            }

            result.addProperty("code", code);
            result.addProperty("message", message);

        } catch (Exception e) {
            code = -1;
            message = "Error";
            this.logger.debug("Exception SignIn Method: {}", e.toString());
        }

        result.addProperty("code", code);
        result.addProperty("message", message);

        return result.toString();
    }

    private String getCredentials(String source, TokenModel token) {
        try {

            Statement statement = connection.createStatement();
            String query = TokenQuery.getQueryToken(source, token);
            this.logger.debug("Query Credenciales: {}", query);

            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                token.setPassword(null);
                token.setType(source);

                if (source.equals(TypeUtils.PARTNER)) {
                    query = String.format("select validar_cliente_bank(%s, %s, %s)",
                            token.getUniqueCustomerIdentifier(),
                            token.getDeviceId(),
                            token.getEmail());

                    this.logger.debug("Query Verificar Cliente: {}", query);

                    statement.executeQuery(query);
                }

                return this.gson.toJson(token);
            }

            return null;

        } catch (Exception e) {
            this.logger.debug("Exception Query Credenciales: {}", e.toString());
            System.out.println(e);
            return null;
        }

    }

}

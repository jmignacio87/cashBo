/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.data.PartnerQuery;
import com.bo.rest.modelos.SearchNearbyModel;
import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.DBConnection;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author aarauco2608
 */
public class SearchNearbyController {

    private Gson gson = new Gson();
    Connection connection = DBConnection.getConnection();

    public String getSearchNearby(String authorization, SearchNearbyModel model) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            } else {
                throw new Exception();
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            TokenModel token = this.gson.fromJson(jsonObject, TokenModel.class);

            return this.getResultSearchNearby(model, token).toString();
        } catch (Exception e) {
            return null;
        }

    }

    private JsonObject getResultSearchNearby(SearchNearbyModel model, TokenModel token) {
        JsonObject response = new JsonObject();
        Integer code = -1;
        String message = "";

        try {
            Statement statement = connection.createStatement();
            String query = PartnerQuery.getQuerySearchNearby(token.getDeviceId());
            ResultSet result = statement.executeQuery(query);
            ArrayList<HashMap<String, Object>> list = new ArrayList<>();

            while (result.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("partnerid", result.getString("partnerid"));
                data.put("address", result.getString("address"));
                data.put("available_end", result.getString("available_end"));
                data.put("available_start", result.getString("available_start"));
                data.put("shop_photo", result.getString("shop_photo"));
                data.put("id", result.getString("id"));
                data.put("name", result.getString("name"));
                data.put("location_lat", result.getString("location_lat"));
                data.put("location_lng", result.getString("location_lng"));
                data.put("cashpoint_description", result.getString("cashpoint_description"));
                data.put("status", result.getString("status"));

                list.add(data);
            }

            code = 0;
            message = "success";

            JsonArray jsonListTransaction = new Gson().toJsonTree(list).getAsJsonArray();
            JsonObject jsonTransaction = new JsonObject();
            jsonTransaction.add("transactions", jsonListTransaction);

            response.add("data", jsonTransaction);

        } catch (Exception e) {
            code = -1;
            message = "Error";
        }

        response.addProperty("code", code);
        response.addProperty("message", message);

        return response;
    }
}

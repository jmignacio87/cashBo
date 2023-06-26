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
            String query = PartnerQuery.getQuerySearchNearby(token.getDeviceId(), model.getLatitude(), model.getLongitude());
            ResultSet result = statement.executeQuery(query);

            ArrayList<Object> list = new ArrayList<>();

            while (result.next()) {
                JsonObject merchant = new JsonObject();
                merchant.addProperty("partnerid", result.getString("partnerid"));
                merchant.addProperty("address", result.getString("address"));
                merchant.addProperty("available_end", result.getString("available_end"));
                merchant.addProperty("available_start", result.getString("available_start"));
                merchant.addProperty("shop_photo", result.getString("shop_photo"));
                merchant.addProperty("id", result.getString("id"));
                merchant.addProperty("name", result.getString("name"));
                merchant.addProperty("location_lat", result.getString("location_lat"));
                merchant.addProperty("location_lng", result.getString("location_lng"));
                merchant.addProperty("cashpoint_description", result.getString("cashpoint_description"));
                merchant.addProperty("status", result.getString("status"));

                JsonObject merchantItem = new JsonObject();
                merchantItem.add("merchant", merchant);

                list.add(merchantItem);
            }

            code = 0;
            message = "success";

            JsonArray merchants = new Gson().toJsonTree(list).getAsJsonArray();

            JsonObject data = new JsonObject();
            data.addProperty("requestId", model.getRequestId());
            data.addProperty("partnerid", token.getDeviceId());
            data.add("merchants", merchants);

            response.add("data", data);

        } catch (Exception e) {
            code = -1;
            message = "Error";
        }

        response.addProperty("code", code);
        response.addProperty("message", message);

        return response;
    }
}

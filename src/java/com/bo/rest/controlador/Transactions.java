/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.modelos.Token;
import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author aarauco2608
 */
public class Transactions {

    private Gson gson = new Gson();

    public Transactions() {
    }

    public String getPendingTransactions(String authorization) {
        try {
            String bearerToken = authorization.split(" ")[1];
            String subjectToken = "";

            if (TokenUtils.verifyJwt(bearerToken)) {
                subjectToken = TokenUtils.getSubject(bearerToken);
            }

            JsonObject jsonObject = new JsonParser().parse(subjectToken).getAsJsonObject();
            Token token = this.gson.fromJson(jsonObject, Token.class);

            return this.gson.toJson(token);
        } catch (Exception e) {
            return "";
        }

    }
}

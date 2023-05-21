/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.controlador;

import com.bo.rest.utils.TokenUtils;
import com.google.gson.Gson;

/**
 *
 * @author aarauco2608
 */
public class Login {

    public Gson gson = new Gson();

    public String singIn(String source, String body) {
        return TokenUtils.generateJwt(body);
    }

}

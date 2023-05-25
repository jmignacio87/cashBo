/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.data;

import com.bo.rest.modelos.Token;
import com.bo.rest.utils.TypeUtils;

/**
 *
 * @author aarauco2608
 */
public class TokenQuery {

    public static String getQueryToken(String source, Token token) {
        String query = null;

        try {
            if (source.equals(TypeUtils.CASHPOINT)) {
                query = String.format("select 1");
            } else if (source.equals(TypeUtils.PARTNER)) {
                query = String.format("select 2");
            }
        } catch (Exception e) {

        }

        return query;
    }
}

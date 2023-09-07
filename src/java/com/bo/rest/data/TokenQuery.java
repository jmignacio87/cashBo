/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.data;

import com.bo.rest.modelos.TokenModel;
import com.bo.rest.utils.TypeUtils;

/**
 *
 * @author aarauco2608
 */
public class TokenQuery {

    public static String getQueryToken(String source, TokenModel token) {
        String query = null;

        try {
            if (source.equals(TypeUtils.CASHPOINT)) {
                query = String.format("select"
                        + " cr.apikey"
                        + ", cr.password"
                        + ", cp.cashpoint_id"
                        + ", pt.id_partner"
                        + " from partner pt, credenciales cr, cashpoint cp"
                        + " where cp.id_partner = pt.id_partner and"
                        + " pt.id_partner = cr.id_partner and"
                        + " cp.cashpoint_id = '%s' and"
                        + " cp.id_partner = '%s' and"
                        + " cr.apiKey = '%s' and"
                        + " cr.password = '%s'", token.getDeviceId(), token.getExternalDeviceId(), token.getApiKey(), token.getPassword());
            } else if (source.equals(TypeUtils.PARTNER)) {
                
                query = String.format("select"
                        + " cc.id_partner as partnerid, cc.apikey as apiKey, cb.uniqueCustomerIdentifier as subject "
                        + " from "
                        + " credenciales cc , customer_bank cb"
                        + " where "
                        + " cb.uniqueCustomerIdentifier = '%s' and"
                        //+ " cb.telefono = '%s' and"
                        //+ " cb.email = '%s' and"
                        + " cc.apikey = '%s' and"
                        + " cc.password = '%s'", token.getUniqueCustomerIdentifier(), /*token.getDeviceId(), token.getEmail(),*/ token.getApiKey(), token.getPassword());
                
            }
        } catch (Exception e) {
            return null;
        }

        return query;
    }
}

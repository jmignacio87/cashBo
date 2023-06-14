/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.data;

/**
 *
 * @author aarauco2608
 */
public class CashpointQuery {

    public static String getQueryTransactionsPending(String deviceId) {
        String query = null;

        query = String.format("select socash_txn_id as socash_txn_id"
                + " from trx_codigopin"
                + " where id_estado_trx = 1 and cashpoint_id = '%s'", deviceId);

        return query;
    }
}

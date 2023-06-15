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

        query = String.format("select socash_txn_id as socash_txn_id, 1 as queue_number"
                + " from trx_codigopin"
                + " where id_estado_trx = 1 and cashpoint_id = '%s'", deviceId);

        return query;
    }

    public static String getQueryTransactionsDetails(String soCashTxnId) {
        return String.format("SELECT"
                + " tcp.id_partner_bank AS partnerid,"
                + " tcp.cashpoint_id AS cashpoint_id,"
                + " cp.nombre_cashpoint AS cashpoint_name,"
                + " tcp.monto AS amount,"
                + " tcp.socash_txn_id AS socash_txn_id,"
                + " tcp.bank_txn_id AS bank_txn_id,"
                + " tcp.id_estado_trx AS transactionStatus,"
                + " tcp.fecha_creacion AS transactionCreationTime,"
                + " tcp.uniqueCustomerIdentifier AS uniqueCustomerIdentifier,"
                + " md.sigla AS withdrawalCurrency,"
                + " tcp.queue_number AS queue_number,"
                + " cp.latitud AS cashpoint_latitude,"
                + " cp.longitud AS cashpoint_longitude"
                + "FROM trx_codigopin tcp,"
                + " cashpoint cp,"
                + " partner pt,"
                + " moneda md"
                + "WHERE tcp.cashpoint_id = '%s'"
                + "  AND tcp.id_estado_trx = 1"
                + "  AND cp.cashpoint_id = tcp.cashpoint_id"
                + "  AND tcp.id_partner_bank = pt.id_partner"
                + "  AND md.id_moneda = pt.id_moneda", soCashTxnId);
    }
}

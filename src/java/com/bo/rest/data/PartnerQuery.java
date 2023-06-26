/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.data;

/**
 *
 * @author aarauco2608
 */
public class PartnerQuery {

    public static String getQuerySearchNearby(String deviceId, String latitude, String longitude) {
        String distance = String.format("SELECT (acos(sin(radians(%s)) * sin(radians(cp.latitud)) "
                + "cos(radians(%s)) * cos(radians(cp.latitud)) * "
                + "cos(radians(%s) - radians(cp.longitud))) * 6371 *1000) as distance", latitude, latitude, longitude);

        return String.format("select pt.id_partner as partnerid,   "
                + " cp.localizacion_cashpoint as address,   "
                + " %s"
                + " cph.apertura as available_end,  "
                + " cph.cierre as available_start,  "
                + " cp.imagen_negocio as shop_photo,  "
                + " cp.cashpoint_id as id,  "
                + " cp.nombre_cashpoint as name,  "
                + " cp.latitud as location_lat,  "
                + " cp.longitud as location_lng,  "
                + " cp.descripcion_cashpoint as cashpoint_description,  "
                + " cp.estado_habilitado as status  "
                + " from partner pt , pbank_cashpoint pbcp, cashpoint cp, cashpoint_horario_atencion cph  "
                + " where pt.id_partner = '%s' and  "
                + " pt.id_partner = pbcp.id_partner_bank and  "
                + " pbcp.cashpoint_id = cp.cashpoint_id and  "
                + " cp.cashpoint_id = cph.cashpoint_id and  "
                + " cph.id_dia = (select "
                + "CASE "
                + "        WHEN DAYOFWEEK(CURDATE()) = 1 THEN 7 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 2 THEN 1 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 3 THEN 2 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 4 THEN 3 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 5 THEN 4 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 6 THEN 5 "
                + "        WHEN DAYOFWEEK(CURDATE()) = 7 THEN 6 "
                + "    END) and   "
                + " (SELECT CURRENT_TIME()) BETWEEN apertura and cierre and  "
                + " cp.estado_habilitado = 'ONLINE'", distance, deviceId);
    }

    public static String getQueryConfiguration(String devideId) {
        return String.format("select pt.id_partner as partnerid,  "
                + " pt.transactiontype as transactiontype,  "
                + " pt.searchradius as searchradius,  "
                + " mnd.sigla as withdrawalCurrency,  "
                + " pt.monto_minimo as minwithdrawalamountpertransaction, "
                + " pt.monto_maximo as maxwithdrawalamountpertransaction, "
                + " pt.monto_maximo_dia as maxwithdrawalamountperday, "
                + " pt.cust_acct_debited as cust_acct_debited"
                + "from partner pt, moneda mnd "
                + "where pt.id_partner = '%s' and "
                + "pt.id_tipo = 1 and"
                + "pt.id_moneda = mnd.id_moneda ", devideId);
    }
}

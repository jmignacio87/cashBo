/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class MerchantModel {

    private String requestId;
    private String transactionSource;
    private String deviceId;
    private Integer debit_status;
    private Integer amount;
    private String cashpoint_id;
    private String position_lat;
    private String position_lng;
    private Integer postalcode;
    private String address;
    private String note;

    public MerchantModel() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTransactionSource() {
        return transactionSource;
    }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDebit_status() {
        return debit_status;
    }

    public void setDebit_status(Integer debit_status) {
        this.debit_status = debit_status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCashpoint_id() {
        return cashpoint_id;
    }

    public void setCashpoint_id(String cashpoint_id) {
        this.cashpoint_id = cashpoint_id;
    }

    public String getPosition_lat() {
        return position_lat;
    }

    public void setPosition_lat(String position_lat) {
        this.position_lat = position_lat;
    }

    public String getPosition_lng() {
        return position_lng;
    }

    public void setPosition_lng(String position_lng) {
        this.position_lng = position_lng;
    }

    public Integer getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(Integer postalcode) {
        this.postalcode = postalcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

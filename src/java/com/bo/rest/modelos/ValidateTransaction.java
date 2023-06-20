/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class ValidateTransaction {

    private String requestId;
    private String transactionSource;
    private String socash_txn_id;
    private String bank_txn_id;
    private Integer random_code;
    private String qr_string;
    private Integer device_sync_code;

    public ValidateTransaction() {
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

    public String getSocash_txn_id() {
        return socash_txn_id;
    }

    public void setSocash_txn_id(String socash_txn_id) {
        this.socash_txn_id = socash_txn_id;
    }

    public String getBank_txn_id() {
        return bank_txn_id;
    }

    public void setBank_txn_id(String bank_txn_id) {
        this.bank_txn_id = bank_txn_id;
    }

    public Integer getRandom_code() {
        return random_code;
    }

    public void setRandom_code(Integer random_code) {
        this.random_code = random_code;
    }

    public String getQr_string() {
        return qr_string;
    }

    public void setQr_string(String qr_string) {
        this.qr_string = qr_string;
    }

    public Integer getDevice_sync_code() {
        return device_sync_code;
    }

    public void setDevice_sync_code(Integer device_sync_code) {
        this.device_sync_code = device_sync_code;
    }

}

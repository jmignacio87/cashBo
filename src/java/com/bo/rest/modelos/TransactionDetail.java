/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class TransactionDetail {

    private String requestId = "";
    private String socash_txn_id = "";
    private String bank_txn_id = "";

    public TransactionDetail() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

}

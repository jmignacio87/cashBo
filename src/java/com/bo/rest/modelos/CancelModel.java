/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class CancelModel {

    private String socash_txn_id;
    private String transactionSource;
    private String requestId;
 
    public CancelModel() {
    }

    public String getSocash_txn_id() {
        return socash_txn_id;
    }

    public String getTransactionSource() {
        return transactionSource;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setSocash_txn_id(String socash_txn_id) {
        this.socash_txn_id = socash_txn_id;
    }

    public void setTransactionSource(String transactionSource) {
        this.transactionSource = transactionSource;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    
     
}

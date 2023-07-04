/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class TransactionModel {

    private String socash_txn_id;
    private Integer queue_number;

    public TransactionModel() {
    }

    public String getSocash_txn_id() {
        return socash_txn_id;
    }

    public void setSocash_txn_id(String socash_txn_id) {
        this.socash_txn_id = socash_txn_id;
    }

    public Integer getQueue_number() {
        return queue_number;
    }

    public void setQueue_number(Integer queue_number) {
        this.queue_number = queue_number;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.modelos;

/**
 *
 * @author aarauco2608
 */
public class Gave {

    private String socash_txn_id;
    private Integer purchase_amount;

    public Gave() {
    }

    public String getSocash_txn_id() {
        return socash_txn_id;
    }

    public void setSocash_txn_id(String socash_txn_id) {
        this.socash_txn_id = socash_txn_id;
    }

    public Integer getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(Integer purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

}

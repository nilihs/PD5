package com.myapplicationdev.android.pd5;

public class Receipt {

    private int receiptId;
    private String cost, title, description;

    public Receipt(int receiptId, String cost, String title, String description) {
        this.receiptId = receiptId;
        this.cost = cost;
        this.title = title;
        this.description = description;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
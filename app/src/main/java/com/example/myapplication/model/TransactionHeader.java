package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class TransactionHeader {
    private String uid;
    private DocumentReference screening;
    private Date date;
    private double price;
    private String payment;

    public TransactionHeader(String uid, DocumentReference screening, Date date, double price, String payment) {
        this.uid = uid;
        this.screening = screening;
        this.date = date;
        this.price = price;
        this.payment = payment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public DocumentReference getScreening() {
        return screening;
    }

    public void setScreening(DocumentReference screening) {
        this.screening = screening;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}

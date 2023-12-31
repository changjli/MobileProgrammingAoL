package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class TransactionHeader {
    private String uid;
    private Date date;
    private double price;
    private String payment;
    private Date screeningDateTime;
    private int seatQty;
    private int studio;

    public TransactionHeader(String uid, Date date, double price, String payment, Date screeningDateTime, int seatQty, int studio) {
        this.uid = uid;
        this.date = date;
        this.price = price;
        this.payment = payment;
        this.screeningDateTime = screeningDateTime;
        this.seatQty = seatQty;
        this.studio = studio;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Date getScreeningDateTime() {
        return screeningDateTime;
    }

    public void setScreeningDateTime(Date screeningDateTime) {
        this.screeningDateTime = screeningDateTime;
    }

    public int getSeatQty() {
        return seatQty;
    }

    public void setSeatQty(int seatQty) {
        this.seatQty = seatQty;
    }

    public int getStudio() {
        return studio;
    }

    public void setStudio(int studio) {
        this.studio = studio;
    }
}

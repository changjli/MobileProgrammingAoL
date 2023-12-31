package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentSnapshot;

public class Promo {
    private String id;
    private String code;
    private int discount;

    public Promo(String id, String code, int discount) {
        this.id = id;
        this.code = code;
        this.discount = discount;
    }

    public Promo(DocumentSnapshot document){
        this.id = document.getId();
        this.code = document.getString("code");
        this.discount = document.getLong("discount").intValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

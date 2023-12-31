package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Food {

    String id;

    String name;

    int price;

    int stock;

    String description;

    String image;

    public Food(String id, String name, int price, int stock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Food(DocumentSnapshot doc) {
        this.id = doc.getId();
        this.name = doc.getString("name");
        this.price = doc.getLong("price").intValue();
        this.stock = doc.getLong("stock").intValue();
        this.description = doc.getString("description");
        this.image = doc.getString("image");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

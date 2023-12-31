package com.example.myapplication.model;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;

public class User {
    private String id;
    private String uid;
    private String phoneNumber;
    private String email;
    private String password;
    private String name;
    private String address;
    private String gender;
    private Date dob;

    private String profileImage;

    // default constructor
    public User() {
        this.id = null;
        this.uid = null;
        this.phoneNumber = null;
        this.email = null;
        this.password = null;
        this.name = null;
        this.address = null;
        this.gender = null;
        this.dob = null;
        this.profileImage = null;
    }

    // constructor when converting document to object
    public User (QueryDocumentSnapshot document){
        this.id = document.getId();
        this.uid = document.getString("uid");
        this.phoneNumber = document.getString("phoneNumber");
        this.email = document.getString("email");
        this.password = document.getString("password");
        this.name = document.getString("name");
        this.address = document.getString("address");
        this.gender = document.getString("gender");
        this.dob = document.getDate("dob");
        this.profileImage = document.getString("profileImage");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {

    private String id;

    private String uid;

    private Date date;

    private double price;

    private String payment;

    private Movie movie;

    private Cinema cinema;

    private Date screeningDateTime;

    private String seatNumber;

    public Ticket(String id, String uid, Date date, double price, String payment, Movie movie, Cinema cinema, Date screeningDateTime, String seatNumber) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.price = price;
        this.payment = payment;
        this.movie = movie;
        this.cinema = cinema;
        this.screeningDateTime = screeningDateTime;
        this.seatNumber = seatNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Date getScreeningDateTime() {
        return screeningDateTime;
    }

    public void setScreeningDateTime(Date screeningDateTime) {
        this.screeningDateTime = screeningDateTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}

package com.example.myapplication.model;

import android.app.DownloadManager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Date;
import java.sql.Time;

public class Screening {
    private String id;
    private Movie movie;
    private Cinema cinema;
    private Date date;

    public Screening(String id, Movie movie, Cinema cinema, Date date) {
        this.id = id;
        this.movie = movie;
        this.cinema = cinema;
        this.date = date;
    }

    public Screening(QueryDocumentSnapshot document){
        this.id = document.getId();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

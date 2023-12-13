package com.example.myapplication.model;

import android.app.DownloadManager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

public class Screening {
    private DocumentReference ref;
    private String id;
    private Movie movie;
    private Cinema cinema;
    private Date date;
    private ArrayList<ScreeningTime> time;

    public Screening(String id, Movie movie, Cinema cinema, Date date) {
        this.id = id;
        this.movie = movie;
        this.cinema = cinema;
        this.date = date;
    }

    public Screening(QueryDocumentSnapshot document){
        this.ref = document.getReference();
        this.id = document.getId();
        time = new ArrayList<>();
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

    public DocumentReference getRef() {
        return ref;
    }

    public void setRef(DocumentReference ref) {
        this.ref = ref;
    }

    public ArrayList<ScreeningTime> getTime() {
        return time;
    }

    public void setTime(ArrayList<ScreeningTime> time) {
        this.time = time;
    }

    public void addTime(QueryDocumentSnapshot document){
        this.time.add(new ScreeningTime(document));
    }
}

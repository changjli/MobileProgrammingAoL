package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Movie implements Parcelable {
    private String id;
    private String title;
    private int duration;
    private Date startDate;
    private Date endDate;
    private String trailerUrl;
    private String imageUrl;
    private String description;
    private String producer;
    private String director;
    private String writer;
    private ArrayList<String> casts;
    private String distributor;
    private double rating;

    public Movie(String id, String title, int duration, Date startDate, Date endDate, String trailerUrl, String imageUrl, String description, String producer, String director, String writer, ArrayList<String> casts, String distributor, double rating) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trailerUrl = trailerUrl;
        this.imageUrl = imageUrl;
        this.description = description;
        this.producer = producer;
        this.director = director;
        this.writer = writer;
        this.casts = casts;
        this.distributor = distributor;
        this.rating = rating;
    }

    public Movie(){

    }

    public Movie(QueryDocumentSnapshot document){
        this.id = document.getId();
        this.title = document.getString("title");
        this.duration = 0;
        this.startDate = document.getDate("startDate");
        this.endDate = document.getDate("endDate");
        this.imageUrl = document.getString("imageUrl");
        this.description = document.getString("description");
        this.producer = document.getString("producer");
        this.director = document.getString("director");
        this.writer = document.getString("writer");
        this.casts = null;
        this.distributor = document.getString("distributor");
        this.rating = 4.5;
    }

    public Movie(DocumentSnapshot document){
        this.id = document.getId();
        this.title = document.getString("title");
        this.duration = 0;
        this.startDate = document.getDate("startDate");
        this.endDate = document.getDate("endDate");
        this.imageUrl = document.getString("imageUrl");
        this.description = document.getString("description");
        this.producer = document.getString("producer");
        this.director = document.getString("director");
        this.writer = document.getString("writer");
        this.casts = null;
        this.distributor = document.getString("distributor");
        this.rating = 4.5;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        duration = in.readInt();
        trailerUrl = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        producer = in.readString();
        director = in.readString();
        writer = in.readString();
        casts = in.createStringArrayList();
        distributor = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public ArrayList<String> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<String> casts) {
        this.casts = casts;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(duration);
        dest.writeString(trailerUrl);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(producer);
        dest.writeString(director);
        dest.writeString(writer);
        dest.writeStringList(casts);
        dest.writeString(distributor);
        dest.writeDouble(rating);
    }
}

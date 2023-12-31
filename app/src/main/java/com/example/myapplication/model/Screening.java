package com.example.myapplication.model;

import android.app.DownloadManager;
import android.os.Parcel;
import android.os.Parcelable;

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

public class Screening implements Parcelable {
    private String id;
    private Movie movie;
    private Cinema cinema;
    private Date date;
    private int price;
    private int studio;
    private ArrayList<ScreeningTime> time;

    public Screening(String id, Movie movie, Cinema cinema, Date date) {
        this.id = id;
        this.movie = movie;
        this.cinema = cinema;
        this.date = date;
    }

    public Screening(QueryDocumentSnapshot document){
        this.id = document.getId();
        this.price = document.getLong("price").intValue();
        this.studio = document.getLong("studio").intValue();
        time = new ArrayList<>();
    }

    protected Screening(Parcel in) {
        id = in.readString();
        movie = in.readParcelable(Movie.class.getClassLoader());
        cinema = in.readParcelable(Cinema.class.getClassLoader());
        price = in.readInt();
        studio = in.readInt();
        time = in.createTypedArrayList(ScreeningTime.CREATOR);
    }

    public static final Creator<Screening> CREATOR = new Creator<Screening>() {
        @Override
        public Screening createFromParcel(Parcel in) {
            return new Screening(in);
        }

        @Override
        public Screening[] newArray(int size) {
            return new Screening[size];
        }
    };

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

    public ArrayList<ScreeningTime> getTime() {
        return time;
    }

    public void setTime(ArrayList<ScreeningTime> time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStudio() {
        return studio;
    }

    public void setStudio(int studio) {
        this.studio = studio;
    }

    public void addTime(QueryDocumentSnapshot document){
        this.time.add(new ScreeningTime(document));
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
        dest.writeParcelable(movie, flags);
        dest.writeParcelable(cinema, flags);
        dest.writeInt(price);
        dest.writeInt(studio);
        dest.writeTypedList(time);
    }
}

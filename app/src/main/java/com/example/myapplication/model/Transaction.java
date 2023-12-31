package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;

public class Transaction implements  Parcelable {
    private String id;

    private String uid;

    private Date date;

    private double price;

    private String payment;

    private Movie movie;

    private Cinema cinema;

    private Date screeningDateTime;

    private ArrayList<Seat> seats = new ArrayList<>();

    private int seatQty;

    private int studio;

    public Transaction(){

    }

    public Transaction(String uid, Date date, double price, String payment, int seatQty) {
        this.uid = uid;
        this.date = date;
        this.price = price;
        this.payment = payment;
        this.seatQty = seatQty;
    }

    public Transaction(DocumentSnapshot document){
        this.id = document.getId();
        this.date = document.getDate("date");
        this.payment = document.getString("payment");
        this.price = document.getLong("price").intValue();
        this.uid = document.getString("uid");
        this.screeningDateTime = document.getDate("screeningDateTime");
        this.seatQty = document.getLong("seatQty").intValue();
        this.studio = document.getLong("studio").intValue();
    }

    protected Transaction(Parcel in) {
        id = in.readString();
        uid = in.readString();
        price = in.readDouble();
        payment = in.readString();
        movie = in.readParcelable(Movie.class.getClassLoader());
        cinema = in.readParcelable(Cinema.class.getClassLoader());
        seats = in.createTypedArrayList(Seat.CREATOR);
        seatQty = in.readInt();
        long tmpDate = in.readLong();
        date = tmpDate == -1 ? null : new Date(tmpDate);
        long tmpDate2 = in.readLong();
        screeningDateTime = tmpDate2 == -1 ? null : new Date(tmpDate2);
        studio = in.readInt();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

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

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public int getSeatQty() {
        return seatQty;
    }

    public void setSeatQty(int seatQty) {
        this.seatQty = seatQty;
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public int getStudio() {
        return studio;
    }

    public void setStudio(int studio) {
        this.studio = studio;
    }

    public DocumentReference getRef(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("transactionHeaders").document(this.id);
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
        dest.writeString(uid);
        dest.writeDouble(price);
        dest.writeString(payment);
        dest.writeParcelable(movie, flags);
        dest.writeParcelable(cinema, flags);
        dest.writeTypedList(seats);
        dest.writeInt(seatQty);
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeLong(screeningDateTime != null ? screeningDateTime.getTime() : -1);
        dest.writeInt(studio);
    }
}

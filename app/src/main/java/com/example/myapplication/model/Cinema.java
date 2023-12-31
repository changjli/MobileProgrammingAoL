package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Cinema implements Parcelable {
    private String id;
    private String location;

    private String phoneNumber;

    private String preciseLocation;

    public Cinema(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public Cinema(QueryDocumentSnapshot document){
        this.id = document.getId();
        this.location = document.getString("location");
        this.phoneNumber = document.getString("phoneNumber");
        this.preciseLocation = document.getString("preciseLocation");
    }

    protected Cinema(Parcel in) {
        id = in.readString();
        location = in.readString();
        phoneNumber = in.readString();
        preciseLocation = in.readString();
    }

    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPreciseLocation() {
        return preciseLocation;
    }

    public void setPreciseLocation(String preciseLocation) {
        this.preciseLocation = preciseLocation;
    }

    public DocumentReference getRef(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("cinemas").document(this.id);
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
        dest.writeString(location);
        dest.writeString(phoneNumber);
        dest.writeString(preciseLocation);
    }
}

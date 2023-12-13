package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class Seat implements Parcelable {

    String ref;
    String id;
    String seatNumber;
    Boolean isAvailable;

    public Seat(String ref, String id, String seatNumber, Boolean isAvailable) {
        this.ref = ref;
        this.id = id;
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
    }

    public Seat(DocumentSnapshot doc){
        this.ref = doc.getReference().getPath();
        this.id = doc.getId();
        this.seatNumber = doc.getString("seatNumber");
        this.isAvailable = doc.getBoolean("availability");
    }

    protected Seat(Parcel in) {
        ref = in.readString();
        id = in.readString();
        seatNumber = in.readString();
        byte tmpIsAvailable = in.readByte();
        isAvailable = tmpIsAvailable == 0 ? null : tmpIsAvailable == 1;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

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
        dest.writeString(ref);
        dest.writeString(id);
        dest.writeString(seatNumber);
        dest.writeByte((byte) (isAvailable == null ? 0 : isAvailable ? 1 : 2));
    }
}

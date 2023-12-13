package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;

public class ScreeningTime implements Parcelable {
    String ref;
    private Date time;

    public ScreeningTime(String ref, Date time) {
        this.ref = ref;
        this.time = time;
    }

    public ScreeningTime(DocumentSnapshot doc){
        this.ref = doc.getReference().getPath();
        this.time = doc.getDate("time");
    }

    // read
    protected ScreeningTime(Parcel in) {
        ref = in.readString();
        long tmpDate = in.readLong();
        time = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<ScreeningTime> CREATOR = new Creator<ScreeningTime>() {
        @Override
        public ScreeningTime createFromParcel(Parcel in) {
            return new ScreeningTime(in);
        }

        @Override
        public ScreeningTime[] newArray(int size) {
            return new ScreeningTime[size];
        }
    };

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
    // write
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(ref);
        dest.writeLong(time != null ? time.getTime() : -1);
    }
}

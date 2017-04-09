package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Date implements Parcelable {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("date")
    @Expose
    public String date;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.date);
    }

    public Date() {}

    protected Date(Parcel in) {
        this.type = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel source) {return new Date(source);}

        @Override
        public Date[] newArray(int size) {return new Date[size];}
    };
}

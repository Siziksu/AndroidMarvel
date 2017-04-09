package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price implements Parcelable {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("price")
    @Expose
    public Double price;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeValue(this.price);
    }

    public Price() {}

    protected Price(Parcel in) {
        this.type = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {return new Price(source);}

        @Override
        public Price[] newArray(int size) {return new Price[size];}
    };
}

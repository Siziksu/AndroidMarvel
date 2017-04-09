package com.siziksu.marvel.common.model.response.characters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnail implements Parcelable {

    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("extension")
    @Expose
    public String extension;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.extension);
    }

    public Thumbnail() {}

    protected Thumbnail(Parcel in) {
        this.path = in.readString();
        this.extension = in.readString();
    }

    public static final Parcelable.Creator<Thumbnail> CREATOR = new Parcelable.Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel source) {return new Thumbnail(source);}

        @Override
        public Thumbnail[] newArray(int size) {return new Thumbnail[size];}
    };
}

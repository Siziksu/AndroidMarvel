package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

    @SerializedName("resourceURI")
    @Expose
    public String resourceURI;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("type")
    @Expose
    public String type;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.resourceURI);
        dest.writeString(this.name);
        dest.writeString(this.role);
        dest.writeString(this.type);
    }

    public Item() {}

    protected Item(Parcel in) {
        this.resourceURI = in.readString();
        this.name = in.readString();
        this.role = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {return new Item(source);}

        @Override
        public Item[] newArray(int size) {return new Item[size];}
    };
}

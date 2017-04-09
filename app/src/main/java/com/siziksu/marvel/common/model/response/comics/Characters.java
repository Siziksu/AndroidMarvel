package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Characters implements Parcelable {

    @SerializedName("available")
    @Expose
    public Integer available;
    @SerializedName("collectionURI")
    @Expose
    public String collectionURI;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;
    @SerializedName("returned")
    @Expose
    public Integer returned;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.available);
        dest.writeString(this.collectionURI);
        dest.writeList(this.items);
        dest.writeValue(this.returned);
    }

    public Characters() {}

    protected Characters(Parcel in) {
        this.available = (Integer) in.readValue(Integer.class.getClassLoader());
        this.collectionURI = in.readString();
        this.items = new ArrayList<Item>();
        in.readList(this.items, Item.class.getClassLoader());
        this.returned = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Characters> CREATOR = new Parcelable.Creator<Characters>() {
        @Override
        public Characters createFromParcel(Parcel source) {return new Characters(source);}

        @Override
        public Characters[] newArray(int size) {return new Characters[size];}
    };
}

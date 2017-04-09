package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {

    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("limit")
    @Expose
    public Integer limit;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("results")
    @Expose
    public List<Comic> comics = null;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.offset);
        dest.writeValue(this.limit);
        dest.writeValue(this.total);
        dest.writeValue(this.count);
        dest.writeTypedList(this.comics);
    }

    public Data() {}

    protected Data(Parcel in) {
        this.offset = (Integer) in.readValue(Integer.class.getClassLoader());
        this.limit = (Integer) in.readValue(Integer.class.getClassLoader());
        this.total = (Integer) in.readValue(Integer.class.getClassLoader());
        this.count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.comics = in.createTypedArrayList(Comic.CREATOR);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {return new Data(source);}

        @Override
        public Data[] newArray(int size) {return new Data[size];}
    };
}

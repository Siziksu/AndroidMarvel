package com.siziksu.marvel.common.model.response.comics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Comic implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("thumbnail")
    @Expose
    public Thumbnail thumbnail;
    @SerializedName("urls")
    @Expose
    public List<Url> urls = null;

    public Comic() {}

    protected Comic(Parcel in) {
        title = in.readString();
        description = in.readString();
        format = in.readString();
        thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.urls = new ArrayList<Url>();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(format);
        dest.writeParcelable(thumbnail, flags);
        dest.writeList(this.urls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}

package com.siziksu.marvel.common.model.response.characters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Character implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("thumbnail")
    @Expose
    public Thumbnail thumbnail;
    @SerializedName("resourceURI")
    @Expose
    public String resourceURI;
    @SerializedName("comics")
    @Expose
    public Comics comics;
    @SerializedName("series")
    @Expose
    public Series series;
    @SerializedName("stories")
    @Expose
    public Stories stories;
    @SerializedName("events")
    @Expose
    public Events events;
    @SerializedName("urls")
    @Expose
    public List<Url> urls = new ArrayList<Url>();

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.modified);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeString(this.resourceURI);
        dest.writeParcelable(this.comics, flags);
        dest.writeParcelable(this.series, flags);
        dest.writeParcelable(this.stories, flags);
        dest.writeParcelable(this.events, flags);
        dest.writeList(this.urls);
    }

    public Character() {}

    protected Character(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.modified = in.readString();
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.resourceURI = in.readString();
        this.comics = in.readParcelable(Comics.class.getClassLoader());
        this.series = in.readParcelable(Series.class.getClassLoader());
        this.stories = in.readParcelable(Stories.class.getClassLoader());
        this.events = in.readParcelable(Events.class.getClassLoader());
        this.urls = new ArrayList<Url>();
        in.readList(this.urls, Url.class.getClassLoader());
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel source) {return new Character(source);}

        @Override
        public Character[] newArray(int size) {return new Character[size];}
    };
}

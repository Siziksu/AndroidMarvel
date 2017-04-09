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
    @SerializedName("digitalId")
    @Expose
    public Integer digitalId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("issueNumber")
    @Expose
    public Integer issueNumber;
    @SerializedName("variantDescription")
    @Expose
    public String variantDescription;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("isbn")
    @Expose
    public String isbn;
    @SerializedName("upc")
    @Expose
    public String upc;
    @SerializedName("diamondCode")
    @Expose
    public String diamondCode;
    @SerializedName("ean")
    @Expose
    public String ean;
    @SerializedName("issn")
    @Expose
    public String issn;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("pageCount")
    @Expose
    public Integer pageCount;
    @SerializedName("textObjects")
    @Expose
    public List<Object> textObjects = null;
    @SerializedName("resourceURI")
    @Expose
    public String resourceURI;
    @SerializedName("urls")
    @Expose
    public List<Url> urls = null;
    @SerializedName("series")
    @Expose
    public Series series;
    @SerializedName("variants")
    @Expose
    public List<Object> variants = null;
    @SerializedName("collections")
    @Expose
    public List<Collection> collections = null;
    @SerializedName("collectedIssues")
    @Expose
    public List<Object> collectedIssues = null;
    @SerializedName("dates")
    @Expose
    public List<Date> dates = null;
    @SerializedName("prices")
    @Expose
    public List<Price> prices = null;
    @SerializedName("thumbnail")
    @Expose
    public Thumbnail thumbnail;
    @SerializedName("images")
    @Expose
    public List<Image> images = null;
    @SerializedName("creators")
    @Expose
    public Creators creators;
    @SerializedName("characters")
    @Expose
    public Characters characters;
    @SerializedName("stories")
    @Expose
    public Stories stories;
    @SerializedName("events")
    @Expose
    public Events events;

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.digitalId);
        dest.writeString(this.title);
        dest.writeValue(this.issueNumber);
        dest.writeString(this.variantDescription);
        dest.writeString(this.description);
        dest.writeString(this.modified);
        dest.writeString(this.isbn);
        dest.writeString(this.upc);
        dest.writeString(this.diamondCode);
        dest.writeString(this.ean);
        dest.writeString(this.issn);
        dest.writeString(this.format);
        dest.writeValue(this.pageCount);
        dest.writeList(this.textObjects);
        dest.writeString(this.resourceURI);
        dest.writeList(this.urls);
        dest.writeParcelable(this.series, flags);
        dest.writeList(this.variants);
        dest.writeList(this.collections);
        dest.writeList(this.collectedIssues);
        dest.writeList(this.dates);
        dest.writeList(this.prices);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeList(this.images);
        dest.writeParcelable(this.creators, flags);
        dest.writeParcelable(this.characters, flags);
        dest.writeParcelable(this.stories, flags);
        dest.writeParcelable(this.events, flags);
    }

    public Comic() {}

    protected Comic(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.digitalId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.issueNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.variantDescription = in.readString();
        this.description = in.readString();
        this.modified = in.readString();
        this.isbn = in.readString();
        this.upc = in.readString();
        this.diamondCode = in.readString();
        this.ean = in.readString();
        this.issn = in.readString();
        this.format = in.readString();
        this.pageCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.textObjects = new ArrayList<Object>();
        in.readList(this.textObjects, Object.class.getClassLoader());
        this.resourceURI = in.readString();
        this.urls = new ArrayList<Url>();
        in.readList(this.urls, Url.class.getClassLoader());
        this.series = in.readParcelable(Series.class.getClassLoader());
        this.variants = new ArrayList<Object>();
        in.readList(this.variants, Object.class.getClassLoader());
        this.collections = new ArrayList<Collection>();
        in.readList(this.collections, Collection.class.getClassLoader());
        this.collectedIssues = new ArrayList<Object>();
        in.readList(this.collectedIssues, Object.class.getClassLoader());
        this.dates = new ArrayList<Date>();
        in.readList(this.dates, Date.class.getClassLoader());
        this.prices = new ArrayList<Price>();
        in.readList(this.prices, Price.class.getClassLoader());
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.images = new ArrayList<Image>();
        in.readList(this.images, Image.class.getClassLoader());
        this.creators = in.readParcelable(Creators.class.getClassLoader());
        this.characters = in.readParcelable(Characters.class.getClassLoader());
        this.stories = in.readParcelable(Stories.class.getClassLoader());
        this.events = in.readParcelable(Events.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel source) {return new Comic(source);}

        @Override
        public Comic[] newArray(int size) {return new Comic[size];}
    };
}

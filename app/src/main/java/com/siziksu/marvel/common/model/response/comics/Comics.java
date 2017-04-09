package com.siziksu.marvel.common.model.response.comics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comics {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("copyright")
    @Expose
    public String copyright;
    @SerializedName("attributionText")
    @Expose
    public String attributionText;
    @SerializedName("attributionHTML")
    @Expose
    public String attributionHTML;
    @SerializedName("etag")
    @Expose
    public String etag;
    @SerializedName("data")
    @Expose
    public Data data;
}

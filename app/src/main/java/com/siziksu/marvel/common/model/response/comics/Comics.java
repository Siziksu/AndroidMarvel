package com.siziksu.marvel.common.model.response.comics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comics {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("data")
    @Expose
    public Data data;
}

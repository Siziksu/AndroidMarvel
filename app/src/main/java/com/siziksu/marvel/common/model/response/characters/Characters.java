package com.siziksu.marvel.common.model.response.characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Characters {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("data")
    @Expose
    public Data data;
}

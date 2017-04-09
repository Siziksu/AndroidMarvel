package com.siziksu.marvel.common.model.request.characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CharactersRequest {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("offset")
    @Expose
    public Integer offset;
    @SerializedName("paginationSize")
    @Expose
    public Integer paginationSize;
}

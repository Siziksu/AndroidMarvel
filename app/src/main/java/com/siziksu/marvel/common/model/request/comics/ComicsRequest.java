package com.siziksu.marvel.common.model.request.comics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComicsRequest {

    @SerializedName("characterId")
    @Expose
    public Integer characterId;
}

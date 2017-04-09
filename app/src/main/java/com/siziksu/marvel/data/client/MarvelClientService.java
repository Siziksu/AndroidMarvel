package com.siziksu.marvel.data.client;

import com.siziksu.marvel.common.model.response.characters.Characters;
import com.siziksu.marvel.common.model.response.comics.Comics;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelClientService {

    String URI = "/v1/public/";
    String URI_GET_CHARACTERS = URI + "characters";
    String URI_GET_COMICS = URI + "characters/{characterId}/comics";

    @GET(URI_GET_CHARACTERS)
    Observable<Characters> getCharacters(
            @Query("ts") Long ts,
            @Query("hash") String hash,
            @Query("apikey") String apiKey,
            @Query("nameStartsWith") String name,
            @Query("offset") Integer offset,
            @Query("limit") Integer limit
    );

    @GET(URI_GET_COMICS)
    Observable<Comics> getComics(
            @Path("characterId") int characterId,
            @Query("ts") Long ts,
            @Query("hash") String hash,
            @Query("apikey") String apiKey
    );
}

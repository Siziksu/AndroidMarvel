package com.siziksu.marvel.domain.main;

import com.siziksu.marvel.BuildConfig;
import com.siziksu.marvel.common.Cryptography;
import com.siziksu.marvel.common.model.request.characters.CharactersRequest;
import com.siziksu.marvel.common.model.response.characters.Characters;
import com.siziksu.marvel.data.client.MarvelClientService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class GetCharactersRequest implements IGetCharactersRequest {

    @Inject
    MarvelClientService service;

    @Inject
    GetCharactersRequest() {}

    @Override
    public Observable<Characters> getCharacters(CharactersRequest request) {
        long ts = System.currentTimeMillis();
        String string = ts + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY;
        String hash = Cryptography.md5(string);
        String apiKey = BuildConfig.API_PUBLIC_KEY;
        return service.getCharacters(ts, hash, apiKey, request.name, request.offset, request.paginationSize).subscribeOn(Schedulers.io());
    }
}

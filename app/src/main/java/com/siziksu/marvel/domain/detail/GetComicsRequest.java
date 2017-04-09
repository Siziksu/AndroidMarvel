package com.siziksu.marvel.domain.detail;

import com.siziksu.marvel.BuildConfig;
import com.siziksu.marvel.common.Cryptography;
import com.siziksu.marvel.common.model.request.comics.ComicsRequest;
import com.siziksu.marvel.common.model.response.comics.Comics;
import com.siziksu.marvel.data.client.MarvelClientService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class GetComicsRequest implements IGetComicsRequest {

    @Inject
    MarvelClientService service;

    @Inject
    GetComicsRequest() {}

    @Override
    public Observable<Comics> getComics(ComicsRequest request) {
        long ts = System.currentTimeMillis();
        String string = ts + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY;
        String hash = Cryptography.md5(string);
        String apiKey = BuildConfig.API_PUBLIC_KEY;
        return service.getComics(request.characterId, ts, hash, apiKey).subscribeOn(Schedulers.io());
    }
}

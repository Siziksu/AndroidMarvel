package com.siziksu.marvel.dagger.module;

import com.siziksu.marvel.domain.detail.GetComicsRequest;
import com.siziksu.marvel.domain.detail.IGetComicsRequest;
import com.siziksu.marvel.domain.main.GetCharactersRequest;
import com.siziksu.marvel.domain.main.IGetCharactersRequest;

import dagger.Module;
import dagger.Provides;

@Module
public final class RequestModule {

    public RequestModule() {}

    @Provides
    IGetCharactersRequest getCharactersRequest(GetCharactersRequest request) {
        return request;
    }

    @Provides
    IGetComicsRequest getComicsRequest(GetComicsRequest request) {
        return request;
    }
}

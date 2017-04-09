package com.siziksu.marvel.domain.detail;

import com.siziksu.marvel.common.model.request.comics.ComicsRequest;
import com.siziksu.marvel.common.model.response.comics.Comics;

import io.reactivex.Observable;

public interface IGetComicsRequest {

    Observable<Comics> getComics(ComicsRequest request);
}

package com.siziksu.marvel.domain.main;

import com.siziksu.marvel.common.model.request.characters.CharactersRequest;
import com.siziksu.marvel.common.model.response.characters.Characters;

import io.reactivex.Observable;

public interface IGetCharactersRequest {

    Observable<Characters> getCharacters(CharactersRequest request);
}

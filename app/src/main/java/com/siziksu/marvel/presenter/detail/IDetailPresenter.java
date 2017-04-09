package com.siziksu.marvel.presenter.detail;

import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.siziksu.marvel.presenter.BasePresenter;

public abstract class IDetailPresenter extends BasePresenter<IDetailView> {

    public abstract void getComics(int characterId);

    public abstract void goToComic(Character character, Comic comic);

    public abstract void goToMarvelCharacter(Character character);
}

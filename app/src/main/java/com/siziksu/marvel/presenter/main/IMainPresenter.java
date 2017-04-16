package com.siziksu.marvel.presenter.main;

import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.presenter.BasePresenter;

public abstract class IMainPresenter extends BasePresenter<IMainView> {

    public abstract void getCharactersFromSwipeRefresh();

    public abstract void getCharacters(int offset);

    public abstract void getFilteredCharacters(String text, int offset);

    public abstract void goToMarvel();

    public abstract void goToCharacterDetail(Character character);
}

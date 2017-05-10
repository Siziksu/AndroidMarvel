package com.siziksu.marvel.presenter.main;

import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.presenter.IBaseView;

import java.util.List;

public interface IMainView extends IBaseView {

    void showCharacters(List<Character> characters, boolean filtered, int totalCharacters);

    void showConnected(boolean value);

    void stopRefreshing();

    void noComicsAvailable();

    void connectionTimeout();
}

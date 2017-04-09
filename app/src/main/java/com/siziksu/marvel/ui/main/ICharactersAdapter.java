package com.siziksu.marvel.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.siziksu.marvel.common.model.response.characters.Character;

import java.util.List;

interface ICharactersAdapter {

    Character getItem(int position);

    int getOffset();

    boolean isEmpty();

    void clear();

    LinearLayoutManager getLayoutManager();

    RecyclerView.OnScrollListener getOnScrollListener();

    void showCharacters(List<Character> list, boolean filtered, boolean more);

    RecyclerView.Adapter getAdapter();

    boolean isFiltered();
}

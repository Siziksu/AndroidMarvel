package com.siziksu.marvel.ui.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.siziksu.marvel.common.model.response.comics.Comic;

import java.util.List;

interface IComicsAdapter {

    Comic getItem(int position);

    int getOffset();

    boolean isEmpty();

    void clear();

    LinearLayoutManager getLayoutManager();

    void showComics(List<Comic> list);

    RecyclerView.Adapter getAdapter();
}

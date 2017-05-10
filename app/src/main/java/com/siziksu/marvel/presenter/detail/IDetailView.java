package com.siziksu.marvel.presenter.detail;

import com.siziksu.marvel.common.model.response.comics.Comic;
import com.siziksu.marvel.presenter.IBaseView;

import java.util.List;

public interface IDetailView extends IBaseView {

    void showComics(List<Comic> comics);

    void showConnected(boolean value);

    void stopRefreshing();

    void socketTimeout();
}

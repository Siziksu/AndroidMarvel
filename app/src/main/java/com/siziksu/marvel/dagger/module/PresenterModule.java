package com.siziksu.marvel.dagger.module;

import com.siziksu.marvel.presenter.detail.DetailPresenter;
import com.siziksu.marvel.presenter.detail.IDetailPresenter;
import com.siziksu.marvel.presenter.main.IMainPresenter;
import com.siziksu.marvel.presenter.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    public PresenterModule() {}

    @Provides
    IMainPresenter getMainPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    IDetailPresenter getDetailPresenter(DetailPresenter presenter) {
        return presenter;
    }
}

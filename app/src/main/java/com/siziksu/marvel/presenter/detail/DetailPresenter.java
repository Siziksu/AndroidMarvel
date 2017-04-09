package com.siziksu.marvel.presenter.detail;

import android.content.Intent;
import android.util.Log;

import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.request.comics.ComicsRequest;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.siziksu.marvel.common.model.response.comics.Comics;
import com.siziksu.marvel.domain.detail.IGetComicsRequest;
import com.siziksu.marvel.ui.comic.ComicActivity;
import com.siziksu.marvel.ui.web.WebActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public final class DetailPresenter extends IDetailPresenter {

    @Inject
    ConnectionManager connectionManager;
    @Inject
    IGetComicsRequest getComicsRequest;

    private boolean requestActive;

    @Inject
    DetailPresenter() {}

    @Override
    public void getComics(int characterId) {
        if (!thereIsConnection()) {
            return;
        }
        if (!requestActive && isViewRegistered()) {
            requestActive = true;
            view.showProgress(true, true);
            ComicsRequest request = new ComicsRequest();
            request.characterId = characterId;
            getComicsRequest.getComics(request)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::onComics,
                                       this::requestError,
                                       this::requestDone);
        }
    }

    private void onComics(Comics response) {
        view.showComics(response.data.comics);
    }

    private void requestError(Throwable throwable) {
        Log.e(Constants.TAG, throwable.getMessage(), throwable);
        requestActive = false;
        if (isViewRegistered()) {
            view.showProgress(false, true);
        }
    }

    private void requestDone() {
        Log.i(Constants.TAG, "Comics request done");
        if (view != null) {
            view.showProgress(false, true);
        }
        requestActive = false;
    }

    private boolean thereIsConnection() {
        boolean isConnected = connectionManager.getConnection().isConnected();
        if (!isConnected) {
            Log.d(Constants.TAG, Constants.NO_CONNECTION);
        }
        if (isViewRegistered()) {
            view.showConnected(isConnected);
        }
        return isConnected;
    }

    @Override
    public void goToMarvelCharacter(Character character) {
        if (isViewRegistered()) {
            if (connectionManager.getConnection().isConnected()) {
                Intent intent = new Intent(view.getActivity(), WebActivity.class);
                intent.putExtra(Constants.EXTRAS_URL, character.urls.get(0).url);
                view.getActivity().startActivity(intent);
            } else {
                view.connectionError();
            }
        }
    }

    @Override
    public void goToComic(Character character, Comic comic) {
        if (isViewRegistered()) {
            Intent intent = new Intent(view.getActivity(), ComicActivity.class);
            intent.putExtra(Constants.EXTRAS_CHARACTER, character);
            intent.putExtra(Constants.EXTRAS_COMIC, comic);
            view.getActivity().startActivity(intent);
        }
    }
}

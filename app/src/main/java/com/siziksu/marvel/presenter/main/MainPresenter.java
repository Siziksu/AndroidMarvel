package com.siziksu.marvel.presenter.main;

import android.content.Intent;
import android.util.Log;

import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.request.characters.CharactersRequest;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.characters.Characters;
import com.siziksu.marvel.domain.main.IGetCharactersRequest;
import com.siziksu.marvel.ui.detail.DetailActivity;
import com.siziksu.marvel.ui.web.WebActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public final class MainPresenter extends IMainPresenter {

    @Inject
    ConnectionManager connectionManager;
    @Inject
    IGetCharactersRequest getCharactersRequest;

    private boolean requestActive;

    @Inject
    MainPresenter() {}

    @Override
    public void getCharacters(int offset, int paginationSize) {
        getCharacters(false, null, offset, paginationSize);
    }

    @Override
    public void getFilteredCharacters(String name, int offset, int paginationSize) {
        getCharacters(true, name, offset, paginationSize);
    }

    private void getCharacters(boolean filtered, String name, int offset, int paginationSize) {
        if (!thereIsConnection()) {
            return;
        }
        if (!requestActive && isViewRegistered()) {
            requestActive = true;
            boolean value = offset == 0;
            view.showProgress(true, value);
            CharactersRequest request = new CharactersRequest();
            request.name = name;
            request.offset = offset;
            request.paginationSize = paginationSize;
            getCharactersRequest.getCharacters(request)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> onCharacters(response, filtered, !value),
                                           throwable -> requestError(throwable, value),
                                           () -> requestDone(value));
        }
    }

    private void onCharacters(Characters response, boolean filtered, boolean more) {
        view.showCharacters(response.data.characters, filtered, more);
    }

    private void requestError(Throwable throwable, boolean swipe) {
        Log.e(Constants.TAG, throwable.getMessage(), throwable);
        hideProgress(swipe);
    }

    private void requestDone(boolean swipe) {
        Log.i(Constants.TAG, "Characters request done");
        hideProgress(swipe);
    }

    private void hideProgress(boolean swipe) {
        if (isViewRegistered()) {
            view.showProgress(false, swipe);
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
    public void goToMarvel() {
        if (isViewRegistered()) {
            if (connectionManager.getConnection().isConnected()) {
                Intent intent = new Intent(view.getActivity(), WebActivity.class);
                intent.putExtra(Constants.EXTRAS_URL, Constants.MARVEL_URL);
                view.getActivity().startActivity(intent);
            } else {
                view.connectionError();
            }
        }
    }

    @Override
    public void goToCharacter(Character character) {
        if (isViewRegistered()) {
            Intent intent = new Intent(view.getActivity(), DetailActivity.class);
            intent.putExtra(Constants.EXTRAS_CHARACTER, character);
            view.getActivity().startActivity(intent);
        }

    }
}

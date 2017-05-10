package com.siziksu.marvel.presenter.main;

import android.content.Intent;
import android.util.Log;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.functions.Consumer;
import com.siziksu.marvel.common.model.request.characters.CharactersRequest;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.characters.Characters;
import com.siziksu.marvel.domain.main.IGetCharactersRequest;
import com.siziksu.marvel.ui.detail.DetailActivity;
import com.siziksu.marvel.ui.web.WebActivity;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public final class MainPresenter extends IMainPresenter {

    @Inject
    ConnectionManager connectionManager;
    @Inject
    IGetCharactersRequest getCharactersRequest;

    private Disposable disposable;
    private boolean swipeOn;

    @Inject
    MainPresenter() {}

    @Override
    public void getCharactersFromSwipeRefresh() {
        swipeOn = true;
        cancelProgress();
        getCharacters(false, null, 0);
    }

    @Override
    public void getCharacters(int offset) {
        cancelSwipeRefresh();
        getCharacters(false, null, offset);
    }

    @Override
    public void getFilteredCharacters(String name, int offset) {
        cancelSwipeRefresh();
        getCharacters(true, name, offset);
    }

    private void getCharacters(boolean filtered, String name, int offset) {
        cancelLastRequest();
        doIfThereIsConnection(() -> doIfViewIsRegistered(() -> {
            if (!swipeOn) {
                view.showProgress(true);
            }
            CharactersRequest request = new CharactersRequest();
            request.name = name;
            request.offset = offset;
            request.paginationSize = Constants.PAGINATION_SIZE;
            disposable = getCharactersRequest.getCharacters(request)
                                             .observeOn(AndroidSchedulers.mainThread())
                                             .subscribe(response -> onCharacters(response, filtered),
                                                        this::onError,
                                                        () -> Log.i(Constants.TAG, "Movies request done"));
        }));
    }

    private void cancelLastRequest() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    private void onCharacters(Characters response, boolean filtered) {
        disposable = null;
        doIfViewIsRegistered(() -> view.showCharacters(response.data.characters, filtered, response.data.total));
        stopProgress();
    }

    private void onError(Throwable throwable) {
        disposable = null;
        Log.e(Constants.TAG, throwable.getMessage(), throwable);
        if (throwable instanceof SocketTimeoutException) {
            doIfViewIsRegistered(() -> view.socketTimeout());
        }
        stopProgress();
    }

    private void cancelProgress() {
        doIfViewIsRegistered(() -> view.showProgress(false));
    }

    private void cancelSwipeRefresh() {
        if (swipeOn) {
            swipeOn = false;
            doIfViewIsRegistered(() -> view.stopRefreshing());
        }
    }

    private void stopProgress() {
        doIfViewIsRegistered(() -> {
            if (swipeOn) {
                view.stopRefreshing();
                swipeOn = false;
            } else {
                view.showProgress(false);
            }
        });
    }

    private void doIfViewIsRegistered(Consumer consumer) {
        if (isViewRegistered()) {
            consumer.consume();
        }
    }

    private void doIfThereIsConnection(Consumer consumer) {
        boolean isConnected = connectionManager.getConnection().isConnected();
        doIfViewIsRegistered(() -> {
            if (!isConnected) {
                Log.d(Constants.TAG, view.getActivity().getResources().getString(R.string.connection_error));
                stopProgress();
            } else {
                consumer.consume();
            }
            view.showConnected(isConnected);
        });
    }

    @Override
    public void goToMarvel() {
        boolean isConnected = connectionManager.getConnection().isConnected();
        doIfViewIsRegistered(() -> {
            if (isConnected) {
                Intent intent = new Intent(view.getActivity(), WebActivity.class);
                intent.putExtra(Constants.EXTRAS_URL, Constants.MARVEL_URL);
                view.getActivity().startActivity(intent);
            }
            view.showConnected(isConnected);
        });
    }

    @Override
    public void goToCharacterDetail(Character character) {
        doIfViewIsRegistered(() -> {
            if (!character.comics.items.isEmpty()) {
                Intent intent = new Intent(view.getActivity(), DetailActivity.class);
                intent.putExtra(Constants.EXTRAS_CHARACTER, character);
                view.getActivity().startActivity(intent);
            } else {
                view.noComicsAvailable();
            }
        });

    }
}

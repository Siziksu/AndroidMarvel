package com.siziksu.marvel.presenter.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.functions.Consumer;
import com.siziksu.marvel.common.model.request.comics.ComicsRequest;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.siziksu.marvel.common.model.response.comics.Comics;
import com.siziksu.marvel.domain.detail.IGetComicsRequest;
import com.siziksu.marvel.ui.detail.DetailBottomSheetFragment;
import com.siziksu.marvel.ui.web.WebActivity;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public final class DetailPresenter extends IDetailPresenter {

    @Inject
    ConnectionManager connectionManager;
    @Inject
    IGetComicsRequest getComicsRequest;

    private Disposable disposable;
    private boolean swipeOn;

    @Inject
    DetailPresenter() {}

    @Override
    public void getComicsFromSwipeRefresh(int characterId) {
        swipeOn = true;
        cancelProgress();
        getComicsFrom(characterId);
    }

    @Override
    public void getComics(int characterId) {
        cancelSwipeRefresh();
        getComicsFrom(characterId);
    }

    private void getComicsFrom(int characterId) {
        cancelLastRequest();
        doIfThereIsConnection(() -> doIfViewIsRegistered(() -> {
            if (!swipeOn) {
                view.showProgress(true);
            }
            ComicsRequest request = new ComicsRequest();
            request.characterId = characterId;
            disposable = getComicsRequest.getComics(request)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(this::onComics,
                                                    this::onError,
                                                    () -> Log.i(Constants.TAG, "Comics request done"));
        }));
    }

    private void cancelLastRequest() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    private void onComics(Comics response) {
        disposable = null;
        doIfViewIsRegistered(() -> view.showComics(response.data.comics));
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
            } else {
                consumer.consume();
            }
            view.showConnected(isConnected);
        });
    }

    @Override
    public void goToMarvelCharacter(Character character) {
        doIfViewIsRegistered(() -> {
            if (connectionManager.getConnection().isConnected()) {
                Intent intent = new Intent(view.getActivity(), WebActivity.class);
                intent.putExtra(Constants.EXTRAS_URL, character.urls.get(0).url);
                view.getActivity().startActivity(intent);
            } else {
                view.showConnected(false);
            }
        });
    }

    @Override
    public void showComicDetail(Comic comic) {
        doIfViewIsRegistered(() -> {
            DetailBottomSheetFragment bottomSheetFragment = DetailBottomSheetFragment.get(comic);
            bottomSheetFragment.show(((AppCompatActivity) view.getActivity()).getSupportFragmentManager(), Constants.FRAGMENT_TAG_MAIN_BOTTOM);
        });
    }
}

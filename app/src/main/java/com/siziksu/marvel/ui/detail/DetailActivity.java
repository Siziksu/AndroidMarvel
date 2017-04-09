package com.siziksu.marvel.ui.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siziksu.marvel.App;
import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.siziksu.marvel.presenter.detail.IDetailPresenter;
import com.siziksu.marvel.presenter.detail.IDetailView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class DetailActivity extends AppCompatActivity implements IDetailView {

    @Inject
    IDetailPresenter detailPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detailTitle)
    TextView titleText;
    @BindView(R.id.detailRecyclerView)
    RecyclerView comics;
    @BindView(R.id.detailSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noData)
    TextView noDataText;
    @BindView(R.id.loading)
    ProgressBar loading;

    private Character character;
    private IComicsAdapter adapter;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        App.get(getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        processExtras();
    }

    private void processExtras() {
        if (getIntent().getExtras().containsKey(Constants.EXTRAS_CHARACTER)) {
            character = getIntent().getExtras().getParcelable(Constants.EXTRAS_CHARACTER);
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        comics.setHasFixedSize(true);
        adapter = new ComicsAdapter(
                this,
                (v, position) -> {
                    Comic comic = adapter.getItem(position);
                    if (comic != null && comic.urls.size() > 0) {
                        detailPresenter.goToComic(character, comic);
                    }
                }
        );
        comics.setAdapter(adapter.getAdapter());
        comics.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        comics.setItemAnimator(new DefaultItemAnimator());
        comics.setLayoutManager(adapter.getLayoutManager());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.clear();
            detailPresenter.getComics(character.id);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailPresenter.register(this);
        if (character != null) {
            if (firstTime) {
                titleText.setText(character.name);
                detailPresenter.getComics(character.id);
                firstTime = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (detailPresenter != null) {
            detailPresenter.unregister();
        }
    }

    @OnClick(R.id.detailFab)
    public void onClick() {
        if (character != null) {
            detailPresenter.goToMarvelCharacter(character);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showProgress(boolean value, boolean swipe) {
        if (swipe) {
            swipeRefreshLayout.setRefreshing(value);
        } else {
            loading.setVisibility(value ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showComics(List<Comic> comics) {
        adapter.showComics(comics);
        noData(!adapter.isEmpty());
    }

    @Override
    public void showConnected(boolean value) {
        noData(value);
        if (!value) {
            connectionError();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void connectionError() {
        Snackbar.make(findViewById(R.id.detailContent), getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show();
    }

    private void noData(boolean value) {
        if (!value) {
            noDataText.setVisibility(View.VISIBLE);
        } else {
            noDataText.setVisibility(View.GONE);
        }
    }
}

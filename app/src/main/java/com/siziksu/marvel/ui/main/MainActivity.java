package com.siziksu.marvel.ui.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siziksu.marvel.App;
import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.functions.Consumer;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.presenter.main.IMainPresenter;
import com.siziksu.marvel.presenter.main.IMainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends AppCompatActivity implements IMainView {

    @Inject
    IMainPresenter mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.charactersRecyclerView)
    RecyclerView characters;
    @BindView(R.id.charactersSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.loading)
    ProgressBar loading;

    private ICharactersAdapter adapter;
    private SearchView searchView;
    private int offset;
    private boolean firstTime = true;
    private String filter;
    private boolean refreshing;
    private int totalCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get(getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        characters.setHasFixedSize(true);
        adapter = new CharactersAdapter(
                this,
                (v, position) -> {
                    Character character = adapter.getItem(position);
                    if (character != null && character.urls.size() > 0) {
                        mainPresenter.goToCharacterDetail(character);
                    }
                },
                filtered -> {
                    if (offset > totalCharacters) {
                        return;
                    }
                    if (!filtered) {
                        mainPresenter.getCharacters(offset);
                    } else {
                        mainPresenter.getFilteredCharacters(filter, offset);
                    }
                }
        );
        characters.addOnScrollListener(adapter.getOnScrollListener());
        characters.setAdapter(adapter.getAdapter());
        characters.setItemAnimator(new DefaultItemAnimator());
        characters.setLayoutManager(adapter.getLayoutManager());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            doAfterClearingAdapter(() -> mainPresenter.getCharactersFromSwipeRefresh());
            if (!searchView.isIconified()) {
                refreshing = true;
                searchView.onActionViewCollapsed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView != null) {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
            } else if (searchView.isIconified() && adapter.isFiltered()) {
                requestFirstPage();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.EXTRAS_OFFSET, offset);
        savedInstanceState.putInt(Constants.EXTRAS_TOTAL_CHARACTERS, totalCharacters);
        savedInstanceState.putString(Constants.EXTRAS_FILTER, filter);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        offset = savedInstanceState.getInt(Constants.EXTRAS_OFFSET);
        totalCharacters = savedInstanceState.getInt(Constants.EXTRAS_TOTAL_CHARACTERS);
        filter = savedInstanceState.getString(Constants.EXTRAS_FILTER);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.register(this);
        if (firstTime) {
            mainPresenter.getCharacters(offset);
            firstTime = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.unregister();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(onQueryTextListener);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        private boolean submit;

        @Override
        public boolean onQueryTextSubmit(String text) {
            submit = true;
            searchView.onActionViewCollapsed();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String text) {
            search(text);
            return false;
        }

        private void search(String text) {
            if (!refreshing) {
                if (!submit) {
                    filter = text;
                    if (!TextUtils.isEmpty(text)) {
                        doAfterClearingAdapter(() -> mainPresenter.getFilteredCharacters(text, 0));
                    } else {
                        requestFirstPage();
                    }
                }
            }
            refreshing = false;
            submit = false;
        }
    };

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showProgress(boolean value) {
        loading.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCharacters(List<Character> characters, boolean filtered, int totalCharacters) {
        Log.i(Constants.TAG, "Current offset: " + offset);
        Log.i(Constants.TAG, "Total characters: " + totalCharacters);
        this.totalCharacters = totalCharacters;
        adapter.showCharacters(characters, filtered, offset > Constants.PAGINATION_SIZE);
        offset = adapter.getOffset();
        noData(!adapter.isEmpty());
    }

    @Override
    public void showConnected(boolean value) {
        noData(value);
        if (!value) {
            connectionError();
        }
    }

    @Override
    public void connectionError() {
        Snackbar.make(findViewById(R.id.mainContent), getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void noComicsAvailable() {
        Snackbar.make(findViewById(R.id.mainContent), getString(R.string.no_comics_available), Snackbar.LENGTH_SHORT).show();
    }

    private void requestFirstPage() {
        doAfterClearingAdapter(() -> mainPresenter.getCharacters(0));
    }

    private void doAfterClearingAdapter(Consumer consumer) {
        adapter.clear();
        consumer.consume();
    }

    @OnClick(R.id.mainFab)
    public void onClick() {
        mainPresenter.goToMarvel();
    }

    private void noData(boolean value) {
        if (!value) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }
}

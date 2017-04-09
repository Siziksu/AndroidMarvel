package com.siziksu.marvel.ui.web;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siziksu.marvel.App;
import com.siziksu.marvel.R;
import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.common.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class WebActivity extends AppCompatActivity {

    @Inject
    ConnectionManager connectionManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.webProgressBar)
    ProgressBar webProgressBar;
    @BindView(R.id.webSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noData)
    TextView noData;

    private WebViewManager webViewManager;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        App.get(getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webViewManager = new WebViewManager(
                (visibility) -> webProgressBar.setVisibility(visibility),
                value -> swipeRefreshLayout.setRefreshing(value)
        );
        webViewManager.setCacheDir(getCacheDir());
        setSupportActionBar(toolbar);
        if (getIntent().getExtras().containsKey(Constants.EXTRAS_URL)) {
            url = getIntent().getExtras().getString(Constants.EXTRAS_URL);
            if (url != null) {
                swipeRefreshLayout.setOnRefreshListener(this::show);
            }
            show();
        }
    }

    private void show() {
        if (thereIsConnection()) {
            noData.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webProgressBar.setVisibility(View.VISIBLE);
            webViewManager.show(url, webView, (progress) -> webProgressBar.setProgress(progress));
        } else {
            noData.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            webProgressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private boolean thereIsConnection() {
        boolean isConnected = connectionManager.getConnection().isConnected();
        if (!isConnected) {
            Log.d(Constants.TAG, Constants.NO_CONNECTION);
        }
        return isConnected;
    }
}
;
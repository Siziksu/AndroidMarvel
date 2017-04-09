package com.siziksu.marvel.ui.web;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

final class WebViewManager {

    private final Callback<Integer> progressListener;
    private final Callback<Boolean> swipeListener;
    private File cacheDir;

    WebViewManager(Callback<Integer> progressListener, Callback<Boolean> swipeListener) {
        this.progressListener = progressListener;
        this.swipeListener = swipeListener;
    }

    @SuppressLint("SetJavaScriptEnabled")
    void show(String defaultUrl, WebView webView, WebViewListener listener) {
        webView.setWebChromeClient(new CustomWebChromeClient(listener));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(cacheDir.toString());
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.loadUrl(Uri.parse(defaultUrl).toString());
    }

    void setCacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    interface WebViewListener {

        void onProgress(int progress);
    }

    private class CustomWebChromeClient extends WebChromeClient {

        private WebViewListener listener;

        CustomWebChromeClient(WebViewListener listener) {
            this.listener = listener;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (listener != null) {
                if (newProgress >= 95) {
                    progressListener.apply(View.GONE);
                }
                if (newProgress >= 50) {
                    swipeListener.apply(false);
                }
                listener.onProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }

    interface Callback<O> {

        void apply(O object);
    }
}

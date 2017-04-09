package com.siziksu.marvel;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import com.siziksu.marvel.dagger.component.AppComponent;
import com.siziksu.marvel.dagger.component.DaggerAppComponent;
import com.siziksu.marvel.dagger.module.AppModule;
import com.siziksu.marvel.dagger.module.NetModule;

public final class App extends Application {

    private AppComponent appComponent;

    public static App get(Application application) {
        return ((App) application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (BuildConfig.DEBUG) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        setUpDagger();
    }

    private void setUpDagger() {
        appComponent = DaggerAppComponent.builder()
                                         .appModule(new AppModule(this))
                                         .netModule(new NetModule())
                                         .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
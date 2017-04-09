package com.siziksu.marvel.dagger.module;

import android.content.Context;

import com.siziksu.marvel.App;
import com.siziksu.marvel.common.ConnectionManager;
import com.siziksu.marvel.dagger.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @AppScope
    ConnectionManager providesConnectionManager(Context context) {
        return new ConnectionManager(context);
    }

}

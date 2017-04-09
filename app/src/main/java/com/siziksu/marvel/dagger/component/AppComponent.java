package com.siziksu.marvel.dagger.component;

import com.siziksu.marvel.dagger.module.AppModule;
import com.siziksu.marvel.dagger.module.NetModule;
import com.siziksu.marvel.dagger.module.PresenterModule;
import com.siziksu.marvel.dagger.module.RequestModule;
import com.siziksu.marvel.dagger.scope.AppScope;
import com.siziksu.marvel.ui.detail.DetailActivity;
import com.siziksu.marvel.ui.main.MainActivity;
import com.siziksu.marvel.ui.web.WebActivity;

import dagger.Component;

@AppScope
@Component(
        modules = {
                AppModule.class,
                PresenterModule.class,
                RequestModule.class,
                NetModule.class
        }
)
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);

    void inject(WebActivity activity);
}

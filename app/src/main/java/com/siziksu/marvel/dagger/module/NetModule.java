package com.siziksu.marvel.dagger.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.siziksu.marvel.dagger.scope.AppScope;
import com.siziksu.marvel.data.client.MarvelClientService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class NetModule {

    private static final String SERVER = "http://gateway.marvel.com:80";
    private static final int CONNECTION_TIMEOUT = 5;

    public NetModule() {}

    @Provides
    @AppScope
    OkHttpClient providesOkHttpClient(Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging);
        return builder.build();
    }

    @Provides
    @AppScope
    Cache providesOkHttpCache(Context context) {
        final int cacheSize = 10485760; // 10 * 1024 * 1024; // 10 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @AppScope
    Converter.Factory providesGsonConverterFactory() {
        Gson gson = new GsonBuilder().create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @AppScope
    Retrofit providesRetrofit(OkHttpClient okHttpClient, Converter.Factory factoryConverter) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(factoryConverter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(SERVER);
        builder.client(okHttpClient);
        return builder.build();
    }

    @Provides
    @AppScope
    MarvelClientService providesSessionClient(Retrofit retrofit) {
        return retrofit.create(MarvelClientService.class);
    }
}

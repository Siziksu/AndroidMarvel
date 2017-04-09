package com.siziksu.marvel.presenter;

import android.app.Activity;

public interface IBaseView {

    Activity getActivity();

    void showProgress(boolean value, boolean swipe);
}

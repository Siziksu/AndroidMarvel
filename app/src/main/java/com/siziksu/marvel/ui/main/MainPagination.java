package com.siziksu.marvel.ui.main;

import android.util.Log;

import com.siziksu.marvel.common.Constants;

import javax.inject.Inject;

public final class MainPagination implements IMainPagination {

    private int offset;
    private int totalCharacters;

    @Inject
    MainPagination() {}

    @Override
    public boolean shouldLoadMore() {
        return !(offset > totalCharacters);
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void reset() {
        offset = 0;
        totalCharacters = 0;
    }

    @Override
    public void setCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
        Log.i(Constants.TAG, "Total characters: " + totalCharacters);
    }

    @Override
    public boolean isNotFirstPage() {
        return offset > Constants.PAGINATION_SIZE;
    }

    @Override
    public int getTotalCharacters() {
        return totalCharacters;
    }

    @Override
    public void setTotalCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
        Log.i(Constants.TAG, "Current offset: " + offset);
    }
}

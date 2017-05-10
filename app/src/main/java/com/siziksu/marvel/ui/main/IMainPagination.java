package com.siziksu.marvel.ui.main;

public interface IMainPagination {

    boolean shouldLoadMore();

    int getOffset();

    void setCharacters(int totalCharacters);

    boolean isNotFirstPage();

    int getTotalCharacters();

    void setTotalCharacters(int nextPage);

    void setOffset(int currentPage);

    void reset();
}

package com.siziksu.marvel.ui.detail;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ComicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IComicsAdapter {

    private static final String PATTERN = ".*image_not_available.*";

    private final List<Comic> comics;
    private final Context context;
    private final ClickListener listener;
    private final GridLayoutManager layoutManager;

    ComicsAdapter(Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.comics = new ArrayList<>();
        int spanCount = context.getResources().getInteger(R.integer.movies_columns);
        layoutManager = new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false);
        return new ComicsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ComicsViewHolder) {
            ComicsViewHolder localHolder = (ComicsViewHolder) holder;
            Comic comic = comics.get(position);
            localHolder.comicName.setText(comic.title);
            String url = comic.thumbnail.path + Constants.PORTRAIT_INCREDIBLE + comic.thumbnail.extension;
            Picasso.with(context)
                   .load(url)
                   .placeholder(R.drawable.marvel_placeholder)
                   .into(localHolder.comicArt);
            Pattern alreadyPattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher alreadyMatcher = alreadyPattern.matcher(url);
            if (alreadyMatcher.matches()) {
                localHolder.comicName.setVisibility(View.VISIBLE);
            } else {
                localHolder.comicName.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    @Override
    public Comic getItem(int position) {
        if (!comics.isEmpty() && position < comics.size()) {
            return comics.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getOffset() {
        return comics.isEmpty() ? 0 : comics.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        return comics.isEmpty();
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public void showComics(List<Comic> list) {
        comics.clear();
        comics.addAll(list);
        Log.i(Constants.TAG, "Total comics: " + comics.size());
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        this.comics.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    interface ClickListener {

        void onClick(View view, int position);
    }
}

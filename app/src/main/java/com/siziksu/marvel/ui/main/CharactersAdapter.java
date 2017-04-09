package com.siziksu.marvel.ui.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

final class CharactersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ICharactersAdapter {

    private final List<Character> characters;
    private final Context context;
    private final ClickListener listener;
    private final EndOfListListener endOfListListener;
    private final OnScrollListener onScrollListener;
    private final AdapterContentManager adapterContentManager;
    private final LinearLayoutManager layoutManager;
    private boolean filtered;

    CharactersAdapter(Context context, ClickListener listener, EndOfListListener endOfListListener) {
        this.context = context;
        this.listener = listener;
        this.adapterContentManager = new AdapterContentManager(context);
        this.characters = new ArrayList<>();
        this.layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        this.onScrollListener = new OnScrollListener();
        this.endOfListListener = endOfListListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_character, parent, false);
        return new CharactersViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharactersViewHolder) {
            CharactersViewHolder localHolder = (CharactersViewHolder) holder;
            Character character = characters.get(position);
            localHolder.characterName.setText(character.name);
            String content = adapterContentManager.getContent(character);
            localHolder.characterAppearsIn.setText(TextUtils.isEmpty(content) ? Constants.NA : content);
            String url = character.thumbnail.path + Constants.PORTRAIT_INCREDIBLE + character.thumbnail.extension;
            Picasso.with(context)
                   .load(url)
                   .placeholder(R.drawable.marvel_placeholder)
                   .into(localHolder.characterArt);
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public Character getItem(int position) {
        if (!characters.isEmpty() && position < characters.size()) {
            return characters.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getOffset() {
        return characters.isEmpty() ? 0 : characters.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        return characters.isEmpty();
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    @Override
    public void showCharacters(List<Character> list, boolean filtered, boolean more) {
        this.filtered = filtered;
        if (!more) {
            characters.clear();
        }
        showAllCharacters(list);
    }

    private void showAllCharacters(List<Character> list) {
        characters.addAll(list);
        Log.i(Constants.TAG, "Total characters: " + characters.size());
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        this.characters.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public boolean isFiltered() {
        return filtered;
    }

    private final class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!characters.isEmpty() && (layoutManager.findLastCompletelyVisibleItemPosition() + 1) == characters.size()) {
                if (endOfListListener != null) {
                    endOfListListener.onEndOfListReached(filtered);
                }
            }
        }
    }

    interface ClickListener {

        void onClick(View view, int position);
    }

    interface EndOfListListener {

        void onEndOfListReached(boolean filtered);
    }
}

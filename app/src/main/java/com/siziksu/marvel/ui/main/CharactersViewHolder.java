package com.siziksu.marvel.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.marvel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

final class CharactersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.characterView)
    View characterView;
    @BindView(R.id.characterName)
    TextView characterName;
    @BindView(R.id.characterAppearsIn)
    TextView characterAppearsIn;
    @BindView(R.id.characterArt)
    ImageView characterArt;

    private CharactersAdapter.ClickListener listener;

    CharactersViewHolder(View view, CharactersAdapter.ClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
        characterView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}

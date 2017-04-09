package com.siziksu.marvel.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siziksu.marvel.R;

final class CharactersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView characterName;
    TextView characterAppearsIn;
    ImageView characterArt;

    private CharactersAdapter.ClickListener listener;

    CharactersViewHolder(View view, CharactersAdapter.ClickListener listener) {
        super(view);
        this.listener = listener;
        RelativeLayout characterRow = (RelativeLayout) view.findViewById(R.id.characterRow);
        characterRow.setOnClickListener(this);
        characterName = (TextView) view.findViewById(R.id.characterName);
        characterAppearsIn = (TextView) view.findViewById(R.id.characterAppearsIn);
        characterArt = (ImageView) view.findViewById(R.id.characterArt);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}

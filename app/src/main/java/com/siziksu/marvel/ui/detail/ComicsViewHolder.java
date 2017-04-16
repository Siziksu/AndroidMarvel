package com.siziksu.marvel.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.marvel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

final class ComicsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.comicView)
    View comicView;
    @BindView(R.id.comicName)
    TextView comicName;
    @BindView(R.id.comicArt)
    ImageView comicArt;

    private ComicsAdapter.ClickListener listener;

    ComicsViewHolder(View view, ComicsAdapter.ClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
        comicView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}


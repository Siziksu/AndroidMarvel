package com.siziksu.marvel.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siziksu.marvel.R;

final class ComicsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView comicName;
    ImageView comicArt;

    private ComicsAdapter.ClickListener listener;

    ComicsViewHolder(View view, ComicsAdapter.ClickListener listener) {
        super(view);
        this.listener = listener;
        RelativeLayout comicRow = (RelativeLayout) view.findViewById(R.id.comicRow);
        comicRow.setOnClickListener(this);
        comicName = (TextView) view.findViewById(R.id.comicName);
        comicArt = (ImageView) view.findViewById(R.id.comicArt);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}


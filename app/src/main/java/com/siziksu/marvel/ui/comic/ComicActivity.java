package com.siziksu.marvel.ui.comic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.model.response.characters.Character;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class ComicActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comicTitle)
    TextView titleText;
    @BindView(R.id.comicArt)
    ImageView comicArtImage;
    @BindView(R.id.comicName)
    TextView comicNameText;
    @BindView(R.id.comicFormat)
    TextView comicFormatText;
    @BindView(R.id.comicDescription)
    TextView comicDescriptionText;

    private Character character;
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        processExtras();
    }

    private void processExtras() {
        if (getIntent().getExtras().containsKey(Constants.EXTRAS_CHARACTER) && getIntent().getExtras().containsKey(Constants.EXTRAS_COMIC)) {
            character = getIntent().getExtras().getParcelable(Constants.EXTRAS_CHARACTER);
            comic = getIntent().getExtras().getParcelable(Constants.EXTRAS_COMIC);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onResume() {
        super.onResume();
        if (character != null && comic != null) {
            titleText.setText(character.name);
            comicNameText.setText(comic.title);
            comicFormatText.setText(comic.format);
            String description = comic.description == null ? Constants.NA : comic.description;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                comicDescriptionText.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
            } else {
                comicDescriptionText.setText(Html.fromHtml(description));
            }
            String url = comic.thumbnail.path + Constants.PORTRAIT_INCREDIBLE + comic.thumbnail.extension;
            Picasso.with(this)
                   .load(url)
                   .placeholder(R.drawable.marvel_placeholder)
                   .into(comicArtImage);
        }
    }
}

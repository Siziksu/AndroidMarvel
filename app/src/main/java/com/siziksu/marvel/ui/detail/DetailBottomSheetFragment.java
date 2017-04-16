package com.siziksu.marvel.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.Constants;
import com.siziksu.marvel.common.TextUtilities;
import com.siziksu.marvel.common.model.response.comics.Comic;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.bottom_sheet_comic_art)
    ImageView movieArt;
    @BindView(R.id.bottom_sheet_comic_title)
    TextView movieTitle;
    @BindView(R.id.bottom_sheet_comic_format)
    TextView movieYear;
    @BindView(R.id.bottom_sheet_comic_description)
    TextView movieOverview;

    private static final String COMIC_TITLE = "title";
    private static final String COMIC_FORMAT = "format";
    private static final String COMIC_DESCRIPTION = "description";
    private static final String COMIC_ART_PATH = "art_path";
    private static final String COMIC_ART_EXTENSION = "art_extension";

    public static DetailBottomSheetFragment get(Comic comic) {
        DetailBottomSheetFragment fragment = new DetailBottomSheetFragment();
        Bundle arguments = new Bundle();
        arguments.putString(COMIC_TITLE, comic.title);
        arguments.putString(COMIC_FORMAT, comic.format);
        arguments.putString(COMIC_DESCRIPTION, comic.description);
        arguments.putString(COMIC_ART_PATH, comic.thumbnail.path);
        arguments.putString(COMIC_ART_EXTENSION, comic.thumbnail.extension);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString(COMIC_TITLE);
        String format = getArguments().getString(COMIC_FORMAT);
        String description = getArguments().getString(COMIC_DESCRIPTION);
        String artPath = getArguments().getString(COMIC_ART_PATH);
        String artExtension = getArguments().getString(COMIC_ART_EXTENSION);
        movieTitle.setText(title);
        movieYear.setText(format);
        movieOverview.setText(!TextUtils.isEmpty(description) ? TextUtilities.fromHtml(description) : getContext().getResources().getString(R.string.no_description_available));
        String url = artPath + Constants.PORTRAIT_INCREDIBLE + artExtension;
        Picasso.with(getActivity())
               .load(url)
               .placeholder(R.drawable.marvel_placeholder)
               .into(movieArt);
    }
}
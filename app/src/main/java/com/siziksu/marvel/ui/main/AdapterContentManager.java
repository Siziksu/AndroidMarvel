package com.siziksu.marvel.ui.main;

import android.content.Context;

import com.siziksu.marvel.R;
import com.siziksu.marvel.common.model.response.characters.Character;

final class AdapterContentManager {

    private final Context context;

    AdapterContentManager(Context context) {
        this.context = context;
    }

    String getContent(Character character) {
        return getComics(character) +
               getSeries(character) +
               getEvents(character) +
               getStories(character);
    }

    private String getComics(Character character) {
        if (!character.comics.items.isEmpty()) {
            return character.comics.items.size() + context.getString(R.string.comics);
        } else {
            return 0 + context.getString(R.string.comics);
        }
    }

    private String getSeries(Character character) {
        if (!character.series.items.isEmpty()) {
            return character.series.items.size() + context.getString(R.string.series);
        } else {
            return 0 + context.getString(R.string.series);
        }
    }

    private String getEvents(Character character) {
        if (!character.events.items.isEmpty()) {
            return character.events.items.size() + context.getString(R.string.events);
        } else {
            return 0 + context.getString(R.string.events);
        }
    }

    private String getStories(Character character) {
        if (!character.stories.items.isEmpty()) {
            return character.stories.items.size() + context.getString(R.string.stories);
        } else {
            return 0 + context.getString(R.string.stories);
        }
    }
}

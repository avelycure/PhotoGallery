package com.avelycure.photogallery.utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * This class is needed to provide recent query suggestions in SearchView
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.avelycure.photogallery.utils.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}

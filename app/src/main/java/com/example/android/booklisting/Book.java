package com.example.android.booklisting;

/**
 * Created by lama on 8/11/2017 AD.
 */

public class Book {
    private final String mTitle;
    private final String mAuthors;
    private final String mBookPageUrl;

    public Book(String mTitle, String mAuthors, String mBookPageUrl) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mBookPageUrl = mBookPageUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public String getmBookPageUrl() {
        return mBookPageUrl;
    }
}
package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.booklisting.SearchUtils.performHttpRequest;

/**
 * Created by lama on 8/12/2017 AD.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private final String queryString;
    private ArrayList<Book> list = new ArrayList<>();

    public BookLoader(Context context, String queryString) {
        super(context);
        this.queryString = queryString;
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        String jsonResponse;
        URL url = SearchUtils.createUrl(queryString);
        try {
            jsonResponse = performHttpRequest(url);
            list = SearchUtils.parseJSON(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}

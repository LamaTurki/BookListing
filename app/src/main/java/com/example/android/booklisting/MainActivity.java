package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {
    private static final String baseUrl = "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";
    private String mSearchQuery;
    private LoaderManager loaderManager;
    private BookAdapter listAdapter;
    private ListView listView;
    private EditText queryEditText;
    private ImageButton searchButton;
    private Boolean dataIsLoaded = false;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        queryEditText = (EditText) findViewById(R.id.search_query);
        searchButton = (ImageButton) findViewById(R.id.search_btn);
        listView = (ListView) findViewById(R.id.listview);
        emptyView = (TextView) findViewById(R.id.empty_view);
        emptyView.setText(R.string.default_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        listAdapter = new BookAdapter(this, 0, new ArrayList<Book>());
        listView.setAdapter(listAdapter);
        loaderManager = getLoaderManager();
        if (savedInstanceState != null) {
            dataIsLoaded = savedInstanceState.getBoolean("loadData");
            emptyView.setText(savedInstanceState.getString("emptyText"));
            if (dataIsLoaded) {
                mSearchQuery = savedInstanceState.getString("query");
                loaderManager.initLoader(0, null, MainActivity.this);
            }
        }


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderManager.destroyLoader(0);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    dataIsLoaded = true;
                    mSearchQuery = baseUrl + queryEditText.getText().toString().replace(" ", "+");
                    loaderManager.initLoader(0, null, MainActivity.this);
                } else {
                    emptyView.setText(R.string.no_connection);
                }
            }
        });
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        emptyView.setText("");
        progressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, mSearchQuery);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        progressBar.setVisibility(View.GONE);
        listAdapter.clear();
        if (data != null && !data.isEmpty())
            listAdapter.addAll(data);
        else
            emptyView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        listAdapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("query", mSearchQuery);
        outState.putBoolean("loadData", dataIsLoaded);
        outState.putString("emptyText", emptyView.getText().toString());
    }
}



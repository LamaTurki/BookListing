package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lama on 8/11/2017 AD.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private final Context mContext;

    public BookAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null)
            rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        final Book currentBook = getItem(position);
        TextView bookTitleView = (TextView) rootView.findViewById(R.id.book_title);
        bookTitleView.setText(currentBook.getmTitle());
        TextView bookAuthorView = (TextView) rootView.findViewById(R.id.book_authors);
        bookAuthorView.setText(currentBook.getmAuthors());
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(currentBook.getmBookPageUrl()));
                mContext.startActivity(webIntent);
            }
        });
        return rootView;
    }

}

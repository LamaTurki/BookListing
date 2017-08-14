package com.example.android.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lama on 8/12/2017 AD.
 */

public final class SearchUtils {


    private SearchUtils() {

    }

    public static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String performHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        String json = "";
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                json = readInputStream(inputStream);
            } else
                Log.e(SearchUtils.class.getName(), "Error, Response code: " + urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return json;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringResponse = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String lineRead = bufferedReader.readLine();
            while (lineRead != null) {
                stringResponse.append(lineRead);
                lineRead = bufferedReader.readLine();
            }
        }
        return stringResponse.toString();
    }

    public static ArrayList<Book> parseJSON(String jsonString) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONObject jsonRoot = new JSONObject(jsonString);
            if (jsonRoot.getInt("totalItems") > 0) {
                JSONArray booksArray = jsonRoot.getJSONArray("items");
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject currentBook = booksArray.getJSONObject(i);
                    JSONObject currentBookInfo = currentBook.getJSONObject("volumeInfo");
                    String bookTitle = currentBookInfo.getString("title");
                    String bookLink = currentBookInfo.getString("previewLink");
                    JSONArray authorsArray = currentBookInfo.getJSONArray("authors");
                    String authors = "";
                    authors += authorsArray.getString(0);
                    for (int index = 1; index < authorsArray.length(); index++) {
                        authors += " and ";
                        authors += authorsArray.getString(index);
                    }
                    bookList.add(new Book(bookTitle, authors, bookLink));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return bookList;
    }
}


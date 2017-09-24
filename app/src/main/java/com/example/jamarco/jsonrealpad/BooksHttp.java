package com.example.jamarco.jsonrealpad;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that will be responsible for establish the connection with server,
 * make the json download and return its object 'livro'
 */
public class BooksHttp {
    public static final String LIVROS_URL_JSON = "https://raw.githubusercontent.com/nglauber/" +
            "dominando_android2/master/livros_novatec.json";

    //establish a new connection
    public static HttpURLConnection connect (String urlFile) throws IOException {
        final int SECONDS = 1000;
        URL url = new URL(urlFile);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(10 * SECONDS);
        connection.setConnectTimeout(15 * SECONDS);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();
        return connection;
    }

    //check if there is connection
    public static boolean hasConnection(Context ctx){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static List<Book> loadBooksJson(){
        try{
            HttpURLConnection connection = connect(LIVROS_URL_JSON);
            int answer = connection.getResponseCode();
            if (answer == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                JSONObject json = new JSONObject(bytesToString(is));
                return readJsonBooks(json);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToString(InputStream is) throws IOException{
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream bigBuffer = new ByteArrayOutputStream();
        int readBytes;
        while ((readBytes = is.read(buffer)) != -1){
            bigBuffer.write(buffer,0,readBytes);
        }
        return new String(bigBuffer.toByteArray(),"UTF-8");
    }

    public static List<Book> readJsonBooks(JSONObject json) throws JSONException {
        List<Book> booksList = new ArrayList<>();
        String category;

        JSONArray jsonNovatec = json.getJSONArray("novatec");
        for (int i = 0; i < jsonNovatec.length(); i++){
            JSONObject jsonCategory = jsonNovatec.getJSONObject(i);
            category = jsonCategory.getString("categoria");
            JSONArray jsonBooks = jsonCategory.getJSONArray("livros");
            for (int j = 0; j < jsonBooks.length(); j++){
                JSONObject jsonBook = jsonBooks.getJSONObject(j);

                Book book = new Book(jsonBook.getString("titulo"),
                        category,
                        jsonBook.getString("autor"),
                        jsonBook.getInt("ano"),
                        jsonBook.getInt("paginas"),
                        jsonBook.getString("capa"));
                booksList.add(book);
            }
        }
        return booksList;
    }

}

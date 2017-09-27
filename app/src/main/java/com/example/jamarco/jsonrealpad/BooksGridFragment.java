package com.example.jamarco.jsonrealpad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamarco on 9/26/2017.
 */

public class BooksGridFragment extends Fragment implements Response.Listener<JSONObject>,
Response.ErrorListener {
    List<Book> mBooks;
    GridView mGridView;
    TextView mTextView;
    ProgressBar mProgressBar;
    ArrayAdapter<Book> mAdapter;

    boolean isRunning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_books_grid,container,false);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.grid_progress_bar);
        mTextView = (TextView) layout.findViewById(R.id.empty_gridview);
        mGridView = (GridView) layout.findViewById(R.id.gridview);
        mGridView.setEmptyView(mTextView);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBooks == null){
            mBooks = new ArrayList<>();
        }
        mAdapter = new BooksGridAdapter(getActivity(),mBooks);
        mGridView.setAdapter(mAdapter);

        if(!isRunning){
            if (BooksHttp.hasConnection(getActivity())){
                startDownload();
            } else {
                mTextView.setText("No Connection");
            }
        } else {
            showProgress(true);
        }
    }

    //show the Progressbar on the screen
    private void showProgress(boolean show){
        if (show){
            mTextView.setText("Downloading books information");
        }
        mTextView.setVisibility(show ? View.VISIBLE:View.GONE);
        mProgressBar.setVisibility(show ? View.VISIBLE:View.GONE);
    }

    public void startDownload(){
        isRunning = true;
        showProgress(true);
        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        BooksHttp.LIVROS_URL_JSON,
                        null,
                        this,
                        this);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        isRunning = false;
        showProgress(false);
        mTextView.setText("Fail to get books");
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        isRunning = false;
        showProgress(false);

        try{
            List<Book> books = BooksHttp.readJsonBooks(jsonObject);
            mBooks.clear();
            mBooks.addAll(books);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}

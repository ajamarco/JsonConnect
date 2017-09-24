package com.example.jamarco.jsonrealpad;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class books_list extends Fragment {
    booksTask mTask;
    List<Book> mBooks;
    ListView mListView;
    TextView mTextView;
    ProgressBar mProgressBar;
    ArrayAdapter<Book> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retain the fragment instance. this is necessary because we don't want to download
        //everything again when the user rotates the screen
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_books_list,container,false);
        mTextView = (TextView) layout.findViewById(R.id.empty);
        mProgressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);
        mListView = (ListView) layout.findViewById(R.id.list);
        //When the listView is empty, the Textview will be showed
        mListView.setEmptyView(mTextView);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initialize the mBooks variable
        if (mBooks == null){
            mBooks = new ArrayList<>();
        }
        //initialize the mAdapter variable and set it to be the ListView Adapter
        mAdapter = new ArrayAdapter<Book>(getActivity(),android.R.layout.simple_list_item_1,mBooks);
        mListView.setAdapter(mAdapter);

        //if mTask is null we check if there is connection. If so, we call the startDownload method
        //if don't, we set the text to show "no connection"
        if (mTask == null){
            if (BooksHttp.hasConnection(getActivity())){
                startDownload();
            } else{
                mTextView.setText("No Connection");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING){
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

    //if mTask is null and it's not being running we create a new instance and execute it
    public void startDownload(){
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
            mTask = new booksTask();
            mTask.execute();
        }
    }

    class booksTask extends AsyncTask<Void,Void,List<Book>> {

        //before retrieve the data from the Internet
        //we show the Progress bar on the screen
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        //load the data from the Internet
        @Override
        protected List<Book> doInBackground(Void... params) {
            return BooksHttp.loadBooksJson();
        }

        //after load the data, hide the progress bar and if the list isn't null
        //it will clear it, add all data and update the adapter
        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            showProgress(false);
            if (books != null){
                mBooks.clear();
                mBooks.addAll(books);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextView.setText("Fail to get books");
            }
        }
    }
}

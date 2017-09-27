package com.example.jamarco.jsonrealpad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Jamarco on 9/26/2017.
 */

public class BooksGridAdapter extends ArrayAdapter<Book> {
    private ImageLoader mImageLoader;

    public BooksGridAdapter(Context context, List<Book> objects){
        super (context,0,objects);
        mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context ctx = parent.getContext();

        if(convertView == null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_book_grid,parent,false);
        }

        NetworkImageView img = (NetworkImageView) convertView.findViewById(R.id.img_cover_grid);
        TextView txt = (TextView) convertView.findViewById(R.id.txt_grid_title);

        Book book = getItem(position);
        txt.setText(book.mTitle);
        img.setImageUrl(book.mCover,mImageLoader);

        return convertView;
    }
}

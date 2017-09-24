package com.example.jamarco.jsonrealpad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jamarco on 9/24/2017.
 */

public class BooksListAdapter extends ArrayAdapter<Book> {
    public BooksListAdapter (Context context, List<Book> objects){
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Book book = getItem(position);

        //create an object of ViewHolder that will have all our book information
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book_list,parent,false);

            //populate the ViewHolder
            holder = new ViewHolder();
            holder.imgCover = (ImageView)convertView.findViewById(R.id.img_cover);
            holder.txtAuthors = (TextView)convertView.findViewById(R.id.txt_authors);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.txt_title);
            holder.txtYear = (TextView)convertView.findViewById(R.id.txt_year);
            holder.txtPage = (TextView)convertView.findViewById(R.id.txt_pages);
            //setting the tag for the variable Holder. If the convertView already exists
            //we just have to get the tag using the getTag() method
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //using the Picasso library to fill the ImageView with the image retrieved from the URL
        Picasso.with(getContext()).load(book.mCover).into(holder.imgCover);
        holder.txtTitle.setText(book.mTitle);
        holder.txtAuthors.setText(book.mAuthor);
        //using String.valueOf to cast the int into a String
        holder.txtYear.setText(String.valueOf(book.mYear));
        //set the text for the pages using a string inside the strings.xml file
        //we also use a variable - %d - to insert the number of pages as well
        holder.txtPage.setText(getContext().getString(R.string.n_pages,book.mPages));

        return convertView;
    }

    //a static class to hold all the information to fill the layout
    static class ViewHolder{
        ImageView imgCover;
        TextView txtTitle;
        TextView txtAuthors;
        TextView txtPage;
        TextView txtYear;
    }
}

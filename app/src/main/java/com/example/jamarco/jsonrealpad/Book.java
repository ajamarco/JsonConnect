package com.example.jamarco.jsonrealpad;

import java.io.Serializable;

/**
 * Created by Jamarco on 9/24/2017.
 */

public class Book implements Serializable {
    public String mTitle;
    public String mCategory;
    public String mAuthor;
    public int mYear;
    public int mPages;
    public String mCover;

    public Book(){

    }

    public Book(String title, String category, String author, int year, int pages, String cover){
        mTitle = title;
        mCategory = category;
        mAuthor = author;
        mYear = year;
        mPages = pages;
        mCover = cover;
    }

    @Override
    public String toString() {
        return mTitle;
    }


}

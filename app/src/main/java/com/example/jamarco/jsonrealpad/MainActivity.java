package com.example.jamarco.jsonrealpad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BookPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new BookPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mPagerAdapter);
    }

    class BookPagerAdapter extends FragmentPagerAdapter{
        BooksGridFragment mGrid;
        books_list mList;

        public BookPagerAdapter(FragmentManager fm){
            super(fm);
            mList = new books_list();
            mGrid = new BooksGridFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? mList : mGrid;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "List" : "Grid";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

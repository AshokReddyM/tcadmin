package com.byt_eye.tcadmin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by Trainee on 7/28/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 1;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TeluguMovieNewsFragment teluguMovieNewsFragment = new TeluguMovieNewsFragment();
                return teluguMovieNewsFragment;

            default:
                return null;
        }

    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public CharSequence getPageTitle(int position) {

    switch (position){
        case 0:
            return "సినిమా వార్తలు";
    }

        return null;

    }
}



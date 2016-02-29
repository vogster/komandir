package com.korovkin.komandir.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.korovkin.komandir.fragment.FromFragment;
import com.korovkin.komandir.fragment.ToFragment;

/**
 * Created by Nik on 28.02.2016.
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new FromFragment();
            case 1:
                return new ToFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

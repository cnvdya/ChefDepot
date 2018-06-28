package com.fooddepot.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fooddepot.activity.cook.CookMenuFragment;
import com.fooddepot.activity.cook.CookOrdersFragment;
import com.fooddepot.activity.cook.CookProfileFragment;
import com.fooddepot.activity.cook.CookMenuListFragment;

/**
 * Created by mudrita on 4/29/18.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    private String[] tabTitles = new String[]{"Menu", "Orders", "Profile"};

    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CookMenuListFragment();
            case 1:
                return new CookOrdersFragment();
            case 2:
                return new CookProfileFragment();
//            case 3:
//                return new CookProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }




}

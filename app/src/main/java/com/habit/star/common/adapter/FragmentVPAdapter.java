package com.habit.star.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundongdong on 2016/10/21.
 */
public class FragmentVPAdapter extends FragmentPagerAdapter {
    List<Fragment> tabs = null;
    List<String> pageTitles = null;
    private FragmentManager fm;

    public FragmentVPAdapter(FragmentManager fm, ArrayList<Fragment> tabs, ArrayList<String> fragPageTitiles) {
        super(fm);
        this.tabs = tabs;
        this.pageTitles = fragPageTitiles;
        this.fm = fm;
    }

    public FragmentVPAdapter(FragmentManager fm, List<Fragment> tabs, List<String> fragPageTitiles) {
        super(fm);
        this.tabs = tabs;
        this.pageTitles = fragPageTitiles;
        this.fm = fm;
    }

    public void addFragment(Fragment fragment, String title) {
        tabs.add(fragment);
        pageTitles.add(title);
    }

    @Override
    public Fragment getItem(int pos) {
        return tabs.get(pos);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitles.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public void setFragments(List<Fragment> fragments) {
        if (this.tabs != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.tabs) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.tabs = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}

package com.tohabit.skip.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;//fragment管理员
    private FragmentManager fm;//fragment数组

    /**
     * 构造函数
     * @param fm fragment管理员对象
     * @param fragmentList fragment数组
     */
    public CommonFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragments=fragmentList;
        this.fm=fm;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

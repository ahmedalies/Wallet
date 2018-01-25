package com.free.ahmed.wallet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.free.ahmed.wallet.Model.Consts;

/**
 * Created by ahmed on 03/10/17.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private String mMonth;

    public TabPagerAdapter(FragmentManager fm, int tabCount, String month) {
        super(fm);
        this.tabCount = tabCount;
        this.mMonth = month;
    }

    public void setMonth(String month){
        this.mMonth = month;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SummeryFragment suFragment = SummeryFragment.newInstance(mMonth, Consts.SUMMERY_FRAGMENT_TYPE);
                return suFragment;
            case 1:
                MoneyFragment mFragment = MoneyFragment.newInstance(mMonth, Consts.MONEY_FRAGMENT_TYPE);
                return mFragment;
            case 2:
                SettingFragment seFragment = SettingFragment.newInstance();
                return seFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}

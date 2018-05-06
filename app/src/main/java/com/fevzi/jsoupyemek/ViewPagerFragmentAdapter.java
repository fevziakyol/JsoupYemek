package com.fevzi.jsoupyemek;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new YemekFragment();
        } else if (position == 1) {
            return new DuyuruFragment();
        } else {
            return new HaberFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Yemekler";
            case 1:
                return "Duyurular";
            case 2:
                return "Haberler";
            default:
                return "";
        }
    }
}

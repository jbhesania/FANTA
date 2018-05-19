package com.example.android.projectfanta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/18/2018.
 */

public class SectionsPageAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> listFrag = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        listFrag.add(fragment);
        stringList.add(title);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listFrag.get(position);
    }

    @Override
    public int getCount() {
        return listFrag.size();
    }
}

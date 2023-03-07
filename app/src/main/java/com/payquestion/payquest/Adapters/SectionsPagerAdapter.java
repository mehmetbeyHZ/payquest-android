package com.payquestion.payquest.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context context;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }

    public void addFragment(Fragment fragment, String title)
    {
        this.titles.add(title);
        this.fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

package com.payquestion.payquest.App;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.payquestion.payquest.Adapters.SectionsPagerAdapter;
import com.payquestion.payquest.AppActivity;
import com.payquestion.payquest.R;


public class CompetitionListFragment extends Fragment {
    ViewGroup view;
    TabLayout tabLayout;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).navigationView.getMenu().getItem(2).setChecked(true);
        view = (ViewGroup) inflater.inflate(R.layout.app_competition_list,container,false);
        viewPager = view.findViewById(R.id.competition_pager);
        tabLayout = view.findViewById(R.id.competition_tab);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getContext());
        sectionsPagerAdapter.addFragment(new CompetitionsAll(),"Yarışmalar");
        sectionsPagerAdapter.addFragment(new CompetitionsRegistered(), "Kayıtlı Yarışmalar");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
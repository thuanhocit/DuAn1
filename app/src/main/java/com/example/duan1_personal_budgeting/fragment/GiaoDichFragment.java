package com.example.duan1_personal_budgeting.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_personal_budgeting.R;
import com.example.duan1_personal_budgeting.tablayout.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class GiaoDichFragment extends Fragment {
    ViewPager2 viewPager2;
    TabLayoutAdapter tabLayoutAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giao_dich, container, false);
        viewPager2 = view.findViewById(R.id.vp2Tab);


        tabLayoutAdapter = new TabLayoutAdapter(getActivity());
        viewPager2.setAdapter(tabLayoutAdapter);


        return view;
    }
}
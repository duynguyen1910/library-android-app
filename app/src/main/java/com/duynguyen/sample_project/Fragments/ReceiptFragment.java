package com.duynguyen.sample_project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.duynguyen.sample_project.Adapters.ViewPager2Adapter;
import com.duynguyen.sample_project.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ReceiptFragment extends Fragment {
    ViewPager2Adapter viewPager2Adapter;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);
        Mapping();
        viewPager2Adapter = new ViewPager2Adapter(requireActivity());
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Receipt State One");
                } else if (position == 1) {
                    tab.setText("Receipt State Two");
                }
            }
        }).attach();


        return view;
    }

    private void Mapping(){
        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);

    }
}
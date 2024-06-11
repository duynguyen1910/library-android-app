package com.duynguyen.sample_project.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.duynguyen.sample_project.Adapters.ViewPager2Adapter;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.w3c.dom.Text;

public class MemberFragment extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPager2Adapter viewPager2Adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);
        Mapping();

        initUI();

        return view;
    }



    private void initUI() {
        viewPager2Adapter = new ViewPager2Adapter(requireActivity());
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                View customView = requireActivity().getLayoutInflater().inflate(R.layout.tab_custom, null);
                ImageView tabIcon = customView.findViewById(R.id.tabIcon);
                TextView tabLabel = customView.findViewById(R.id.tabLabel);

                if (position == 0) {
                    tabLabel.setText("Librarian");
                    tabIcon.setImageResource(R.drawable.ic_account_box);
                } else if (position == 1) {
                    tabLabel.setText("Customer");
                    tabIcon.setImageResource(R.drawable.ic_customer);
                }

                tab.setCustomView(customView);
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView tabLabel = customView.findViewById(R.id.tabLabel);
                    tabLabel.setTextColor(ContextCompat.getColor(requireActivity(), R.color.primary_color));

                    ImageView tabIcon = customView.findViewById(R.id.tabIcon);
                    tabIcon.setColorFilter(new PorterDuffColorFilter(R.color.primary_color, PorterDuff.Mode.SRC_IN));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView tabLabel = customView.findViewById(R.id.tabLabel);
                    tabLabel.setTextColor(ContextCompat.getColor(requireActivity(), R.color.gray1));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle tab reselection if needed
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void Mapping() {
        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);
    }
}
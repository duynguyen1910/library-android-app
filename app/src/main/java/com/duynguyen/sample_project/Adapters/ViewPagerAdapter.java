package com.duynguyen.sample_project.Adapters;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.duynguyen.sample_project.Fragments.BookFragment;
import com.duynguyen.sample_project.Fragments.CategoryFragment;
import com.duynguyen.sample_project.Fragments.HomeFragment;
import com.duynguyen.sample_project.Fragments.MemberFragment;
import com.duynguyen.sample_project.Fragments.ProfileFragment;
import com.duynguyen.sample_project.Fragments.ReceiptFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CategoryFragment());
        fragmentList.add(new ReceiptFragment());
        fragmentList.add(new MemberFragment());
        fragmentList.add(new ProfileFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        notifyDataSetChanged();
    }

    public void replaceFragment(int position, Fragment fragment) {
        fragmentList.set(position, fragment);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.size() > position ? fragmentTitleList.get(position) : null;
    }
}

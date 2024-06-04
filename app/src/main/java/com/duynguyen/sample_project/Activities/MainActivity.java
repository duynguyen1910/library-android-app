package com.duynguyen.sample_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.duynguyen.sample_project.Adapters.ViewPagerAdapter;
import com.duynguyen.sample_project.Fragments.BookFragment;
import com.duynguyen.sample_project.Fragments.CategoryFragment;
import com.duynguyen.sample_project.Models.Category;
import com.duynguyen.sample_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements
        CategoryFragment.OnCategorySelectedListener, BookFragment.OnBackButtonClickListener {
    private BottomNavigationView bottomNavigation;
    private ViewPager mViewPager;
    private TextView toolbarTitleTv;
    private ImageView backImv;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.parseColor("#F04D7F"));
        getWindow().setNavigationBarColor(Color.WHITE);
        getSupportActionBar().hide();



        bottomNavigation = findViewById(R.id.bottomNavigation);
        mViewPager = findViewById(R.id.viewPager);
        toolbarTitleTv = findViewById(R.id.toolbarTitleTv);
        backImv = findViewById(R.id.backImv);

        toolbarTitleTv.setText("Trang chủ");
        setupViewPager();

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();


                if (itemId == R.id.homeMenu) {
                    mViewPager.setCurrentItem(0);
                    toolbarTitleTv.setText("Trang chủ");
                } else if (itemId == R.id.categoryMenu) {
                    mViewPager.setCurrentItem(1);
                    toolbarTitleTv.setText("Danh mục");
                } else if (itemId == R.id.receiptMenu) {
                    mViewPager.setCurrentItem(2);
                    toolbarTitleTv.setText("Phiếu mượn");
                } else if (itemId == R.id.memberMenu) {
                    mViewPager.setCurrentItem(3);
                    toolbarTitleTv.setText("Thành viên");
                } else if (itemId == R.id.profileMenu) {
                    mViewPager.setCurrentItem(4);
                    toolbarTitleTv.setText("Hồ sơ");
                } else {
                    Log.w("Navigation", "Unknown menu item selected");
                    return false;
                }

                return true;
            }
        });
    }

    private void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = viewPagerAdapter.getItem(position);
                if (fragment instanceof BookFragment) {
                    backImv.setVisibility(View.VISIBLE);
                } else {
                    backImv.setVisibility(View.GONE);
                }


                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.homeMenu).setChecked(true);
                        toolbarTitleTv.setText("Trang chủ");
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.categoryMenu).setChecked(true);
                        toolbarTitleTv.setText("Danh mục");
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.receiptMenu).setChecked(true);
                        toolbarTitleTv.setText("Phiếu mượn");
                        break;
                    case 3:
                        bottomNavigation.getMenu().findItem(R.id.memberMenu).setChecked(true);
                        toolbarTitleTv.setText("Thành viên");
                        break;
                    case 4:
                        bottomNavigation.getMenu().findItem(R.id.profileMenu).setChecked(true);
                        toolbarTitleTv.setText("Hồ sơ");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid position: " + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onCategorySelected(Category category) {
        try {
            BookFragment bookFragment = BookFragment.newInstance(category);
            viewPagerAdapter.addFragment(bookFragment, "Book");
            mViewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);
        } catch (Exception e) {
            Log.e("MainActivity", "Error adding fragment", e);
        }

    }

    @Override
    public void onBackButtonClicked() {
        Log.d("AAAAAAA: ", "");
//        if (mViewPager.getCurrentItem() > 0) {
//            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
//            if (mViewPager.getCurrentItem() == 0) {
//                backImv.setVisibility(View.GONE);
//            }
//        } else {
//            finish();
//        }
    }
}
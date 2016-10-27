package com.example.ngoquan.demotabs.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ngoquan.demotabs.R;
import com.example.ngoquan.demotabs.fragment.EightFragment;
import com.example.ngoquan.demotabs.fragment.FiveFragment;
import com.example.ngoquan.demotabs.fragment.FourFragment;
import com.example.ngoquan.demotabs.fragment.NineFragment;
import com.example.ngoquan.demotabs.fragment.OneFragment;
import com.example.ngoquan.demotabs.fragment.SevenFragment;
import com.example.ngoquan.demotabs.fragment.SixFragment;
import com.example.ngoquan.demotabs.fragment.TenFragment;
import com.example.ngoquan.demotabs.fragment.ThreeFragment;
import com.example.ngoquan.demotabs.fragment.TwoFragment;

public class ScrollableTabsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");
        adapter.addFrag(new FourFragment(), "FOUR");
        adapter.addFrag(new FiveFragment(), "FIVE");
        adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");
        viewPager.setAdapter(adapter);
    }

}

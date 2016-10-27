package com.example.ngoquan.demotabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnSimpleTabs, btnScrollableTabs, btnIconTabs, btnTextIconTabs, btnCustomIconTextTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSimpleTabs = (Button) findViewById(R.id.btnSimpleTabs);
        btnScrollableTabs = (Button) findViewById(R.id.btnScrollableTabs);
        btnIconTabs = (Button) findViewById(R.id.btnIconTabs);
        btnTextIconTabs = (Button) findViewById(R.id.btnIconTextTabs);
        btnCustomIconTextTabs = (Button) findViewById(R.id.btnCustomIconTabs);


        btnSimpleTabs.setOnClickListener(this);
        btnScrollableTabs.setOnClickListener(this);
        btnIconTabs.setOnClickListener(this);
        btnTextIconTabs.setOnClickListener(this);
        btnCustomIconTextTabs.setOnClickListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpleTabs:
                startActivity(new Intent(MainActivity.this, SimpleTabsActivity.class));
                break;
            case R.id.btnScrollableTabs:
                startActivity(new Intent(MainActivity.this, ScrollableTabsActivity.class));
                break;
            case R.id.btnIconTabs:
                startActivity(new Intent(MainActivity.this, IconTabsActivity.class));
                break;
            case R.id.btnIconTextTabs:
                startActivity(new Intent(MainActivity.this, IconTextTabsActivity.class));
                break;
            case R.id.btnCustomIconTabs:
                startActivity(new Intent(MainActivity.this, CustomViewIconTextTabsActivity.class));
                break;
        }
    }
}

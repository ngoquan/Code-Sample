package com.example.ngoquan.demoslideuplayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BottomSheetsActivity extends AppCompatActivity {

    BottomSheetBehavior bottomSheetBehavior;
    Button btnOpen, btnCollapse, btnHide;
    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        View bottomSheet = (View) findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        setUp();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        tvHeading.setText("Welcome");
                        tvHeading.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case MotionEvent.ACTION_DOWN:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        tvHeading.setText("Collapsed");
                        tvHeading.setTextColor(getResources().getColor(R.color.colorAccent));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        tvHeading.setText("Collapsed");
                        tvHeading.setTextColor(getResources().getColor(R.color.colorAccent));
                        break;
                }
                return false;
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setUp() {
        btnOpen = (Button) findViewById(R.id.open);
        btnCollapse = (Button) findViewById(R.id.collapse);
        btnHide = (Button) findViewById(R.id.hide);
        tvHeading = (TextView) findViewById(R.id.heading);

//        handling movement of bottom sheet from buttons
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                tvHeading.setText("Welcome");
                tvHeading.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                tvHeading.setText("Collapsed");
                tvHeading.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

//        Handling movement of bottom sheets from sliding
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tvHeading.setText("Collapsed");
                    tvHeading.setTextColor(ContextCompat.getColor(BottomSheetsActivity.this, R.color.colorAccent));
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tvHeading.setText("Welcome");
                    tvHeading.setTextColor(ContextCompat.getColor(BottomSheetsActivity.this, R.color.colorPrimary));
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });
    }

}

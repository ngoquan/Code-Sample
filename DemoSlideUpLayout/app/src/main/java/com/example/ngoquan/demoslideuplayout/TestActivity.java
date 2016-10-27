package com.example.ngoquan.demoslideuplayout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

public class TestActivity extends AppCompatActivity {

    private ViewGroup hiddenPanel;
    private boolean isPanelShown;
    private RelativeLayout rlHidden;
//    private Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        hiddenPanel = (ViewGroup) findViewById(R.id.hidden_panel);
        rlHidden = (RelativeLayout) findViewById(R.id.rl_hidden);
        rlHidden.setVisibility(View.GONE);
//        hiddenPanel.setVisibility(View.INVISIBLE);
        isPanelShown = false;

    }

    public void slideUpDown(final View view) {
        if(!isPanelShown) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.bottom_up);

            rlHidden.startAnimation(bottomUp);
//            hiddenPanel.setVisibility(View.VISIBLE);
            rlHidden.setVisibility(View.VISIBLE);
            isPanelShown = true;
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.bottom_down);

            rlHidden.startAnimation(bottomDown);
//            hiddenPanel.setVisibility(View.INVISIBLE);
            rlHidden.setVisibility(View.GONE);
            isPanelShown = false;
        }
    }

}

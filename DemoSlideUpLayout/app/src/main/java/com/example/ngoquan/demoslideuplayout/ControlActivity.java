package com.example.ngoquan.demoslideuplayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUseLib, btnNotUseLib, btnBottomSheets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        btnUseLib = (Button) findViewById(R.id.btn_useLib);
        btnNotUseLib = (Button) findViewById(R.id.btn_notUseLib);
        btnBottomSheets = (Button) findViewById(R.id.btn_bottomSheets);

        btnUseLib.setOnClickListener(this);
        btnNotUseLib.setOnClickListener(this);
        btnBottomSheets.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_useLib:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_notUseLib:
                Intent intent1 = new Intent(this, TestActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_bottomSheets:
                Intent intent2 = new Intent(this, BottomSheetsActivity.class);
                startActivity(intent2);
                break;
        }
    }
}

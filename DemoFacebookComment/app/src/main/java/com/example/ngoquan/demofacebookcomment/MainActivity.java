package com.example.ngoquan.demofacebookcomment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/*
* http://www.androidhive.info/2016/09/android-adding-facebook-comments-widget-in-app/
* */
public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://api.androidhive.info/facebook/firebase_analytics.html";

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        webView = (WebView) findViewById(R.id.web_view);

//        loading web pages
        webView.loadUrl(URL);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launching facebook comments activity
                Intent intent = new Intent(MainActivity.this, FacebookCommentActivity.class);

//                Facebook comment cho mọi url. Chỉ cần thay đổi url truyền đi, là ta có thể thực hiện comment với url đó.
                // passing the article url
//                intent.putExtra("url", "http://www.androidhive.info/2016/06/android-firebase-integrate-analytics/");
                intent.putExtra("url", "https://www.ucan.vn/");
                startActivity(intent);
            }
        });

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
}

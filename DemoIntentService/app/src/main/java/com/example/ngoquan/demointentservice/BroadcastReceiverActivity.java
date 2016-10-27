package com.example.ngoquan.demointentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BroadcastReceiverActivity extends AppCompatActivity {


    private MyWebRequestReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        IntentFilter filter = new IntentFilter(MyWebRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyWebRequestReceiver();
        registerReceiver(receiver, filter);

        Button addButton = (Button) findViewById(R.id.sendRequest);
        assert addButton != null;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent(BroadcastReceiverActivity.this, MyWebRequestService.class);
//                msgIntent.putExtra(MyWebRequestService.REQUEST_STRING, "http://google.com");
//                startService(msgIntent);

                msgIntent.putExtra(MyWebRequestService.REQUEST_STRING, "https://www.youtube.com/watch?v=n0J3Th2ACTI&list=PL5_c5Ihvn13AdgqcGxGhFtvxihuHFqc7l&index=22");
                startService(msgIntent);

                msgIntent.putExtra(MyWebRequestService.REQUEST_STRING, "http://www.mysamplecode.com/2011/10/android-intentservice-example-using.html");
                startService(msgIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class MyWebRequestReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.example.ngoquan.demointentservice.intent.action.PROCESS_RESPONSE";
        @Override
        public void onReceive(Context context, Intent intent) {
            String responseString = intent.getStringExtra(MyWebRequestService.RESPONSE_STRING);
            String responseMessage = intent.getStringExtra(MyWebRequestService.RESPONSE_MESSAGE);

            Log.d("ResultProgress", responseString);

            TextView myTextView = (TextView) findViewById(R.id.response);
            myTextView.setText(responseString);

            TextView myResponseMessage = (TextView) findViewById(R.id.responseMessage);
            myResponseMessage.setText(responseMessage);

//            WebView myWebView = (WebView) findViewById(R.id.myWebView);
//            myWebView.getSettings().setJavaScriptEnabled(true);
//            try {
//                myWebView.loadData(URLEncoder.encode(responseMessage, "utf-8").replaceAll("\\+"," "), "text/html", "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }
    }
}

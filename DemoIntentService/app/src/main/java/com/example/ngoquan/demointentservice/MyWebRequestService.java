package com.example.ngoquan.demointentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ngoquan.demointentservice.app.AppController;

import java.text.DateFormat;

/**
 * Created by NGOQUAN on 8/1/2016.
 */
public class MyWebRequestService extends IntentService {

    public static final String REQUEST_STRING = "myRequest";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";

    private String URL = null;
    private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    private static final int WAIT_TIMEOUT = 30 * 1000;


    public static final String TAG = "MyWebRequestService";

    // This tag will be used to cancel the request
    private String tag_string_req = "string_req";
    String responseMessage;


    public MyWebRequestService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String requestString = intent.getStringExtra(REQUEST_STRING);
        URL = requestString;
        String responseString = requestString + " - " + android.text.format.DateFormat.format("dd/MM/yyyy h:maa", System.currentTimeMillis());

//        cho ngủ 10 giây
        SystemClock.sleep(10000);
        Log.d(TAG, responseString);
//
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                responseMessage = response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseMessage = error.toString();
                Log.e(TAG, error.getMessage());
            }
        });



//        Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BroadcastReceiverActivity.MyWebRequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_STRING, responseString);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        sendBroadcast(broadcastIntent);
    }
}

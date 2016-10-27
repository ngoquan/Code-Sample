package com.example.ngoquan.demousevolley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.ngoquan.demousevolley.app.AppController;
import com.example.ngoquan.demousevolley.utils.Const;

public class StringRequestActivity extends AppCompatActivity {

    private String TAG = StringRequestActivity.class.getSimpleName();
    private Button btnStringReq;
    private TextView msgResponse;
    private ProgressDialog pDialog;

    // This tag will be used to cancel the request
    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);
        btnStringReq = (Button) findViewById(R.id.btnStringReq);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnStringReq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeStringReq();
            }
        });

    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
    /**
     * Making string request
     * */

    private void makeStringReq() {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_STRING_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                msgResponse.setText(response.toString());
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);


    }


}

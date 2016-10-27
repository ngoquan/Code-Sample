package com.example.ngoquan.demointentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ngoquan.demointentservice.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NGOQUAN on 8/1/2016.
 */
public class DownloadService extends IntentService {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";
    private String tag_json_objcet = "json_object_request";


    public DownloadService() {
        super(DownloadService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

        JsonObjectRequest jsonObjectRequest = null;
        final Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(url)) {
            receiver.send(STATUS_RUNNING, bundle.EMPTY);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());
                    String [] blogTitles = null;
                    try {
//                        lấy dữ liệu thông qua json object
                        JSONObject jsonObj = new JSONObject(response.toString());
                        JSONArray posts = jsonObj.getJSONArray("posts");
                        if (posts.length() > 0) {
                            Log.d(TAG, posts.length() + "");
                            blogTitles = new String[posts.length()];
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.getJSONObject(i);
                                String title = post.getString("title");
                                blogTitles[i] = title;
                            }
                        }
//                        Gửi kết quả trở lại activity vừa gọi
                        if (blogTitles != null && blogTitles.length > 0) {
                            bundle.putStringArray("result", blogTitles);
                            receiver.send(STATUS_FINISHED, bundle);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
//                    Gửi thông báo lỗi tới Activity
                    bundle.putString(Intent.EXTRA_TEXT, error.getMessage());
                    receiver.send(STATUS_ERROR, bundle);
                }
            });
        }
// thêm request vào hàng đợi của volley để chờ xử lý, tại một thời điểm chỉ 1 request được thực hiện
        if (jsonObjectRequest != null)
            AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_objcet);
        Log.d(TAG, "Service stopping!");
        this.stopSelf();
    }
}

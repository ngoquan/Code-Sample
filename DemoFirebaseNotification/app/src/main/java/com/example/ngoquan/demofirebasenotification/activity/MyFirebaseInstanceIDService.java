package com.example.ngoquan.demofirebasenotification.activity;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NGOQUAN on 7/21/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIdService";

    @Override
    public void onTokenRefresh() {
//        Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }
}

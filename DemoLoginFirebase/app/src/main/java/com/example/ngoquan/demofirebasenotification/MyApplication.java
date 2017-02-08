package com.example.ngoquan.demofirebasenotification;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NGOQUAN on 1/20/2017.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // storing data offline
        // khi không có kết nối internet dữ liệu người dùng vẫn được lưu trữ dưới thiết bị,
        // sau khi có internet sẽ tự động cập nhật dữ liệu
        // Chú ý phải đặt ở đầu phương thức onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Firebase.setAndroidContext(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}

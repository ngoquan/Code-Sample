package com.example.ngoquan.demonotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by NGOQUAN on 8/8/2016.
 */
public class NotificationService extends Service {


    private final String TAG = "NotificationService";
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Notification status;

    private void showNotification() {
//        Sử dụng RemoteViews để custom layout cho Notification
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.status_bar);
        Log.d(TAG, getPackageName());
        RemoteViews bigViews = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);
//        Hiển thị album ảnh mặc định
        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art, Constants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent pPreviousIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pNextIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pCloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pPlayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pPlayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pNextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pNextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, pPreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, pPreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pCloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pCloseIntent);

        views.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_pause);
        bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.apollo_holo_dark_pause);

        views.setTextViewText(R.id.status_bar_track_name, "Song Title");
        bigViews.setTextViewText(R.id.status_bar_track_name, "Song Title");

        views.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
        bigViews.setTextViewText(R.id.status_bar_artist_name, "Artist Name");

        bigViews.setTextViewText(R.id.status_bar_album_name, "Album Name");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            status = new Notification.Builder(this).build();
            status.contentView = views;
            status.bigContentView = bigViews;
            status.flags = Notification.FLAG_ONGOING_EVENT;
            status.icon = R.mipmap.ic_launcher;
            status.contentIntent = pendingIntent;
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
        }




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            showNotification();
            Toast.makeText(NotificationService.this, "Service Started", Toast.LENGTH_SHORT).show();
        } else if(intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Toast.makeText(NotificationService.this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Clicked Previous");
        } else if(intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Clicked Play");
        } else if(intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Toast.makeText(NotificationService.this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Clicked Next");
        } else if(intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.d(TAG, "Received stop Foreground Intent");
            Toast.makeText(NotificationService.this, "Service Stopped", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }


}

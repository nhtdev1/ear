package com.ear.ear.service;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ear.ear.EarPlugin;
import com.ear.ear.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Set;

public class NotificationService extends NotificationListenerService {
    private final static String TAG = NotificationService.class.getSimpleName();

    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        try{
            // Get package name to set as title.
            String packageName = sbn.getPackageName();
            // Get extra object from notification
            Bundle extras = sbn.getNotification().extras;
            String packageMessage = extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            String packageText = extras.getCharSequence("android.title").toString();
            String packageExtra = convertBumbleToJsonString(sbn.getNotification().extras);
            // Pass data from one activity to another.
            Intent mIntent = new Intent(Config.notification_intent);
            mIntent.putExtra(Config.notication_package_name, packageName);
            mIntent.putExtra(Config.notication_message, packageMessage);
            mIntent.putExtra(Config.notication_text, packageText);
            mIntent.putExtra(Config.notication_extra, packageExtra);
            LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);
        }catch (Exception e){
            Log.w(TAG,"An error occurred when get info from notification");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    private String convertBumbleToJsonString(Bundle bundle) {
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                json.put(key, JSONObject.wrap(bundle.get(key)));
            } catch (JSONException e) {
                return "json_exception";
            }
        }

        return json.toString();
    }
}

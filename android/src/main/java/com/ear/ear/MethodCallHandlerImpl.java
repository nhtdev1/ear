package com.ear.ear;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ear.ear.config.Config;
import com.ear.ear.service.NotificationService;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.HashMap;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;


/**
 * The MethodCallHandlerImpl  implements an MethodCallHandler that
 * communicate with flutter layer
 *
 * @author nht.studio.vn@gmail.com
 * @version 0.0.4
 * @since 2021-11-04
 */
public class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {

    private final Context mContext;
    @Nullable
    private Activity activity;

    MethodCallHandlerImpl(Context mContext) {
        this.mContext = mContext;
        mContext.startService(new Intent(mContext, NotificationService.class));
    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case "register":
                Intent intent = new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                if (activity != null) {
                    if (!checkNotificationEnabled(activity))
                        activity.startActivity(intent);
                    result.success(true);
                } else {
                    result.success(false);
                }
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        return false;
    }


    public BroadcastReceiver createDeviceConnectStateChangeReceiver(final EventChannel.EventSink events) {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();

                if (bundle == null) {
                    return;
                }

                if (intent.getAction().equals(Config.notification_intent)) {
                    String packageName = intent.getStringExtra(Config.notication_package_name);
                    String message = intent.getStringExtra(Config.notication_message);
                    String text = intent.getStringExtra(Config.notication_text);
                    String extra = intent.getStringExtra(Config.notication_extra);
                    HashMap<String, Object> payload = new HashMap<>();
                    payload.put("action", "new_notification");
                    payload.put("packageName", packageName);
                    payload.put("message", message);
                    payload.put("text", text);
                    payload.put("extra", extra);
                    payload.put("timestamp", new Timestamp(System.currentTimeMillis()).getTime());
                    if (events != null) events.success(new Gson().toJson(payload));
                    return;
                }
            }
        };
    }

    public void setActivity(@Nullable Activity activity) {
        this.activity = activity;
    }

    /**
     * This method is used to check whether user has accept notification setting or not yet
     *
     * @param activity
     */
    private boolean checkNotificationEnabled(@Nullable Activity activity) {
        try {
            if (Settings.Secure.getString(activity.getContentResolver(),
                    "enabled_notification_listeners").contains(mContext.getPackageName())) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

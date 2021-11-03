package com.ear.ear;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ear.ear.config.Config;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * EarPlugin
 *
 * @Author: nhoangthinh@tma.com.vn
 * Created: 01/05/2021
 * Updated: 26/10/2021
 */
public class EarPlugin implements FlutterPlugin, ActivityAware {
    private final static String TAG = EarPlugin.class.getSimpleName();

    private MethodChannel methodChannel;

    @Nullable
    private MethodCallHandlerImpl methodCallHandler;

    // ---------------------------------------------------------------------------------------------
    // Flutter plugin register

    @Override
    public void onAttachedToEngine(@NonNull final FlutterPluginBinding binding) {
        startListening(binding.getApplicationContext(), binding.getBinaryMessenger()); // or getFlutterEngine().getDartExecutor()
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
//        final EarPlugin instance = new EarPlugin();
//        registrar.addRequestPermissionsResultListener(instance);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        stopListening();
    }

    // ---
    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        startListeningToActivity(binding.getActivity());
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {
        stopListeningToActivity();
    }

    private void startListening(final Context applicationContext, BinaryMessenger messenger) {
        methodChannel = new MethodChannel(messenger, Config.command_channel);

        methodCallHandler = new MethodCallHandlerImpl(
                applicationContext
        );

        new EventChannel(messenger, Config.event_channel)
                .setStreamHandler(
                        new EventChannel.StreamHandler() {
                            private BroadcastReceiver broadcastReceiver;

                            @Override
                            public void onListen(Object arguments, EventChannel.EventSink events) {
                                broadcastReceiver = methodCallHandler.createDeviceConnectStateChangeReceiver(events);
                                LocalBroadcastManager.getInstance(applicationContext).registerReceiver(broadcastReceiver, new IntentFilter(Config.notification_intent));
                            }

                            @Override
                            public void onCancel(Object arguments) {
                                LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(broadcastReceiver);
                                broadcastReceiver = null;
                            }
                        }
                );

        methodChannel.setMethodCallHandler(methodCallHandler);

    }

    private void stopListening() {
        methodChannel.setMethodCallHandler(null);
        methodChannel = null;
        methodCallHandler = null;
    }

    private void startListeningToActivity(
            Activity activity
    ) {
        if (methodCallHandler != null) {
            methodCallHandler.setActivity(activity);
        }
    }

    private void stopListeningToActivity() {
        if (methodCallHandler != null) {
            methodCallHandler.setActivity(null);
        }
    }

}

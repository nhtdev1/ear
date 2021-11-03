package com.ear.ear.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ear.ear.config.Config;

public class SmsReceiver extends BroadcastReceiver {
    private final static String TAG = SmsReceiver.class.getSimpleName();

    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Intent mIntent = new Intent(Config.notification_intent);
                    mIntent.putExtra(Config.notication_package_name, "");
                    mIntent.putExtra(Config.notication_message, message);
                    mIntent.putExtra(Config.notication_text, senderNum);
                    mIntent.putExtra(Config.notication_extra, "");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);
                }
            } // bundle is null
        } catch (Exception e) {
            Log.w(TAG, "An error occurred when get info from sms");
        }
    }
}
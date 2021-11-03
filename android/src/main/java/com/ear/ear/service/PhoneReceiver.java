package com.ear.ear.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ear.ear.config.Config;

public class PhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Intent intent = new Intent(Config.notification_intent);
                intent.putExtra(Config.notication_package_name, "SMS");
                intent.putExtra(Config.notication_message, incomingNumber);
                intent.putExtra(Config.notication_text, incomingNumber);
                intent.putExtra(Config.notication_extra, "");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}

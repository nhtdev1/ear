package com.ear.ear.config;

import android.os.Build;

public class Config {
    public static final String command_channel = "ear/command";
    public static final String event_channel = "ear/event";

    public static final String notification_intent  = "com.ear.ear.notification_intent";
    public static final String notication_package_name  = "com.ear.ear.notication_package_name";
    public static final String notication_message  = "com.ear.ear.notication_message";
    public static final String notication_text  = "com.ear.ear.notication_text";
    public static final String notication_extra  = "com.ear.ear.notication_extra";

    public static boolean buildVersionO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}

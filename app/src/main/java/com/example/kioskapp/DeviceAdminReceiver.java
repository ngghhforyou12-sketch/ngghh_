package com.example.kioskapp;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }
}

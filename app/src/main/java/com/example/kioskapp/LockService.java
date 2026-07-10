package com.example.kioskapp;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class LockService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminComponent = new ComponentName(this, DeviceAdminReceiver.class);

        if (dpm.isDeviceOwnerApp(getPackageName())) {
            startLockTask();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
  }

package com.example.kioskapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String PASSWORD = "666";
    private DevicePolicyManager dpm;
    private ComponentName adminComponent;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "KioskApp:WakeLock");
        wakeLock.acquire(10*60*1000L);

        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, DeviceAdminReceiver.class);

        if (dpm.isDeviceOwnerApp(getPackageName())) {
            startLockTask();
        } else {
            Toast.makeText(this, "Jalankan: adb shell dpm set-device-owner com.example.kioskapp/.DeviceAdminReceiver", Toast.LENGTH_LONG).show();
        }

        EditText etPassword = findViewById(R.id.etPassword);
        Button btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(v -> {
            String input = etPassword.getText().toString();
            if (PASSWORD.equals(input)) {
                stopLockTask();
                if (wakeLock != null && wakeLock.isHeld()) {
                    wakeLock.release();
                }
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
                etPassword.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Tidak bisa kembali
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
          }

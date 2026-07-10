package com.example.kiosk;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String PASSWORD = "666";
    private DevicePolicyManager dpm;
    private ComponentName adminComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Layar tetap menyala
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Inisialisasi DevicePolicyManager
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, DeviceAdminReceiver.class);

        // Coba kunci langsung (hanya jalan jika Device Owner)
        if (dpm.isDeviceOwnerApp(getPackageName())) {
            startLockTask();
        } else {
            Toast.makeText(this, "Jalankan ADB: dpm set-device-owner", Toast.LENGTH_LONG).show();
        }

        // Setup tombol keluar dengan password
        Button btnExit = findViewById(R.id.btnExit);
        EditText etPassword = findViewById(R.id.etPassword);
        btnExit.setOnClickListener(v -> {
            if (etPassword.getText().toString().equals(PASSWORD)) {
                stopLockTask();
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Nonaktifkan tombol back biar gak bisa keluar
        // Tidak melakukan apa-apa
    }
}

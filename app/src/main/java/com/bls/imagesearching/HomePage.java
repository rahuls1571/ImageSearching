package com.bls.imagesearching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {

    private int request_code_permission = 101;
    private String[] request_permission = { Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
    };
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);

        timer = new Timer();
        getSupportActionBar().hide();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               if(check_net_connection())
               {
                   Intent intent = new Intent(HomePage.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
               else
               {

                   Toast.makeText(HomePage.this, "Internet Not Connected", Toast.LENGTH_SHORT).show();
               }
            }
        },2000);


    }



    private boolean All_permission_granted() {
        for (String permission : request_permission) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //start camera when permissions have been granted otherwise exit app
        if (requestCode == request_code_permission) {
            if (All_permission_granted()) {
                // startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public boolean check_net_connection() {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo active_net = manager.getActiveNetworkInfo();
        if (null != active_net) {
            if (active_net.getType() == ConnectivityManager.TYPE_MOBILE || active_net.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        if (null == active_net) {
            return false;
        }

        return false;
    }
}
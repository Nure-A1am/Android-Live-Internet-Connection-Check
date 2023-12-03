package com.example.internetcheck;


import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private final String LOG_TAG = "MainActivity";
    private ConnectivityBroadcast connectivityBroadcast;
    protected ConnectivityDetectionHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityBroadcast = new ConnectivityBroadcast();
        conn = new ConnectivityDetectionHelper();
    }

    // Handle the different broadcast actions
    private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ConnectivityBroadcast.INTERNET_AVAILABLE_ACTION:
                        Log.d(LOG_TAG, "Internet Available!");
                        // Handle the scenario when the internet is available
                        showToast("Internet Available");
                        break;
                    case ConnectivityBroadcast.NO_INTERNET_ACTION:
                        Log.d(LOG_TAG, "No Internet Connection!");
                        // Handle the scenario when there's no internet connection
                        showToast("Data Connection Have No Internet");
                        break;
                    case ConnectivityBroadcast.NO_CONNECTION_ACTION:
                        Log.d(LOG_TAG, "No Active Network Connection!");
                        // Handle the scenario when there's no active network connection
                        showToast("No Data Connection Is Enabled");
                        break;
                    case ConnectivityBroadcast.CONNECTION_ENABLED_ACTION:
                        Log.d(LOG_TAG, "Network Connection Enabled!");
                        // Handle the scenario when the network connection is enabled
                        showToast("Data Connection Available");
                        break;
                }
            }
        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {

        // Register the BroadcastReceiver with the intent values to be matched
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityBroadcast.INTERNET_AVAILABLE_ACTION);
        filter.addAction(ConnectivityBroadcast.NO_INTERNET_ACTION);
        filter.addAction(ConnectivityBroadcast.NO_CONNECTION_ACTION);
        filter.addAction(ConnectivityBroadcast.CONNECTION_ENABLED_ACTION);
        registerReceiver(connectivityBroadcast, filter);

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // Unregister the BroadcastReceiver when the activity is destroyed
        if (connectivityBroadcast != null) {
            unregisterReceiver(connectivityBroadcast);
        }
        super.onDestroy();
    }
}
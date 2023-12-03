package com.example.internetcheck;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ConnectivityBroadcast extends BroadcastReceiver {

    private final String LOG_TAG = "ConnectivityBroadcast";
    public static final String INTERNET_AVAILABLE_ACTION = "INTERNET_AVAILABLE_ACTION";
    public static final String NO_INTERNET_ACTION = "NO_INTERNET_ACTION";
    public static final String NO_CONNECTION_ACTION = "NO_CONNECTION_ACTION";
    public static final String CONNECTION_ENABLED_ACTION = "CONNECTION_ENABLED_ACTION";


    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityDetectionHelper connectivityHelper = new ConnectivityDetectionHelper();

        connectivityHelper.isInternetAvailable(new InternetConnectionCallback() {
            @Override
            public void onInternetConnectionChecked(boolean isAvailable) {
                if (connectivityHelper.isConnectionEnabled(context)) {
                    Log.d(LOG_TAG, "Connection Available!!");
                    Intent connectionEnabledIntent = new Intent(CONNECTION_ENABLED_ACTION);
                    context.sendBroadcast(connectionEnabledIntent);

                    if (isAvailable) {
                        Log.d(LOG_TAG, "You're online!");
                        // Handle the scenario when the internet is available
                        Intent internetAvailableIntent = new Intent(INTERNET_AVAILABLE_ACTION);
                        context.sendBroadcast(internetAvailableIntent);
                    } else {
                        Log.d(LOG_TAG, "You're disconnected from the internet!!");
                        // Handle the scenario when there's no internet connection
                        Intent noInternetIntent = new Intent(NO_INTERNET_ACTION);
                        context.sendBroadcast(noInternetIntent);
                    }
                } else {
                    Log.d(LOG_TAG, "No Connection Available!!");
                    // Handle the scenario when there's no active network connection
                    Intent noConnectionIntent = new Intent(NO_CONNECTION_ACTION);
                    context.sendBroadcast(noConnectionIntent);
                }
            }
        });
    }

}

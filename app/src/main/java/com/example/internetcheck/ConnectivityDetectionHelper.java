package com.example.internetcheck;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConnectivityDetectionHelper {
    private static final String LOG_TAG = "ConChk";
    private HttpURLConnection Urlc = null;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void isInternetAvailable(final InternetConnectionCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean isAvailable = false;
                try {
                    Log.d(LOG_TAG, "Checking internet connection in background...");
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL url = new URL("http://clients3.google.com/generate_204");
                    Urlc = (HttpURLConnection) url.openConnection();
                    Urlc.setRequestProperty("User-Agent", "Test");
                    Urlc.setRequestProperty("Connection", "close");
                    Urlc.setConnectTimeout(500);
                    Urlc.connect();

                    if (Urlc.getResponseCode() == 204 && Urlc.getContentLength() == 0) {
                        Log.d(LOG_TAG, "Internet connection Available!");
                        isAvailable = true;
                    } else {
                        isAvailable = false;
                        Log.d(LOG_TAG, "No! Internet connection.");
                    }

                } catch (IOException e) {
                    Log.d(LOG_TAG, "Error: " + e.toString());
                    e.printStackTrace();
                    isAvailable = false;
                }

                // Notify the calling activity of the result
                callback.onInternetConnectionChecked(isAvailable);
            }
        });
    }

    public boolean isConnectionEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile_data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(mobile_data != null && mobile_data.isConnected() || wifi != null && wifi.isConnected())
        {
            return true;
        }
        else{
            Log.d(LOG_TAG, "Internet connection disconnected.");
            return false;
        }
    }

}



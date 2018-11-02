package com.modosmart.symbiote.modosmartsymbioteandroid.network;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionTest {
    /*
     *  must add these permissions requests to manifest file
     *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *  <uses-permission android:name="android.permission.INTERNET">
     *
     *  Instead on constructor used currently which receives the context of calling activity we can use
     *  this.getBaseContext() or this.getApplicationContext() but the current method is preferred
     * */

    private Context context;

    public ConnectionTest(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null) {
            // Check for network connections
            if (connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.CONNECTED) {

                // if connected with internet
                return true;

            } else if (
                    connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connectivityManager.getActiveNetworkInfo().getState() == android.net.NetworkInfo.State.DISCONNECTED) {

                return false;
            }
        }
        return false;

        /*
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;*/
    }
}

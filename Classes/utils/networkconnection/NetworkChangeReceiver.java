package com.euclid.uptiiq.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.euclid.uptiiq.utils.ApiHelper.ConnectionDetector;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (checkInternet(context)) {
            alert(true);
        } else {
            alert(false);
        }
    }

    protected void alert(boolean b){

    }

    boolean checkInternet(Context context) {
        ConnectionDetector connectionDetector = new ConnectionDetector(context);
        return connectionDetector.isConnectingToInternet();
    }

}

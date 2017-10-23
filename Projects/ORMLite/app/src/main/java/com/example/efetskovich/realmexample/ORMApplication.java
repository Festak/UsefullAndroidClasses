package com.example.efetskovich.realmexample;

import android.app.Application;

import com.example.efetskovich.realmexample.ormlite.ORMLiteFactory;

/**
 * @author e.fetskovich on 10/13/17.
 */

public class ORMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ORMLiteFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        ORMLiteFactory.releaseHelper();
        super.onTerminate();
    }

}

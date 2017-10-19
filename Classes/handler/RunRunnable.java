package com.example.efetskovich.handlerclass;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author e.fetskovich on 10/6/17.
 */

public class RunRunnable {

    private static final String TAG = "RunRunnable";

    private Handler handler;
    private static final int START_SHOWING = 10;
    private static final int STOP_SHOWING = 20;
    private ProgressDialog progressDialog;

    public RunRunnable(Context context){
        initHandler();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading");
    }

    public void run(final Runnable runnable){
        handler.sendEmptyMessage(START_SHOWING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(START_SHOWING);
                runnable.run();
                handler.sendEmptyMessage(STOP_SHOWING);
            }
        }).start();
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case START_SHOWING:
                        progressDialog.show();
                        break;
                    case STOP_SHOWING:
                        progressDialog.hide();
                        break;
                }
            }
        };
    }

}

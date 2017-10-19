package com.euclid.uptiiq.listeners;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.euclid.uptiiq.R;
import com.euclid.uptiiq.constants.SharedPreferenceConstants;
import com.euclid.uptiiq.utils.SharedPreferenceHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author e.fetskovich on 9/19/17.
 */

public abstract class DoubleClickListener implements View.OnClickListener {
    private Timer timer = null;  //at class level;
    private int DELAY   = 400;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            processDoubleClickEvent(v);
        } else {
            processSingleClickEvent(v);
        }
        lastClickTime = clickTime;
    }



    public void processSingleClickEvent(final View v){

        final Handler handler=new Handler();
        final Runnable mRunnable=new Runnable(){
            public void run(){
                onSingleClick(v); //Do what ever u want on single click

            }
        };

        TimerTask timertask=new TimerTask(){
            @Override
            public void run(){
                handler.post(mRunnable);
            }
        };
        timer=new Timer();
        timer.schedule(timertask,DELAY);

    }


    public void processDoubleClickEvent(View v){
        if(timer!=null)
        {
            timer.cancel(); //Cancels Running Tasks or Waiting Tasks.
            timer.purge();  //Frees Memory by erasing cancelled Tasks.
        }
        onDoubleClick(v);//Do what ever u want on Double Click
    }

    public abstract void onSingleClick(View v);

    public abstract void onDoubleClick(View v);

      watermarkText.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.watermarkTotalText) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
            return false;
        }
    });
}

    private void initWatermarkInitialState() {
        watermarkText.setText(SharedPreferenceHelper.readPreference(this, SharedPreferenceConstants.WATERMARK_TEXT, ""));
        if (watermarkText.getText().length() != 0) {
            convertTextToImage(false);
        } else {
            convertTextToImage(true);
        }
        selectedTextWatermarkSize(SharedPreferenceHelper.readPreference(this, SharedPreferenceConstants.WATERMARK_SIZE, "M"));
    }


    private void initWatermarkDoubleTap() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.watermarkTextParent);
        watermarkText.setEnabled(true);
        watermarkText.setFocusable(false);
        layout.setOnClickListener(getOnClickListener());
        watermarkText.setOnClickListener(getOnClickListener());
    }


    private View.OnClickListener getOnClickListener() {
        return new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (watermarkText.hasFocus()) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (!inputMethodManager.isAcceptingText()) {
                        inputMethodManager.showSoftInput(watermarkText, 0);
                    } else if (watermarkText.isCursorVisible()) {
                        inputMethodManager.showSoftInput(watermarkText, 0);
                    }

                }
            }

            @Override
            public void onDoubleClick(View v) {
                changeStateByDoubleClick();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        v.getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);
            }
        };
    }

}

package com.example.kevin.activitylistener;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

public class MyBroadcastReceiver {
    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;

    public MyBroadcastReceiver(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    public void startObserver(ScreenStateListener listener){
        mScreenStateListener = listener;
        registerListener();
        getScreenState();
    }

    public void endObserver(){
        unregisterListener();
    }

    // get screen state
    @SuppressLint("NewApi")
    private void getScreenState(){
        if(mContext == null)
            return;
        PowerManager manager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if(manager.isScreenOn()){
            if(mScreenStateListener != null)
                mScreenStateListener.onScreenOn(); //?? potential error
        }
        /*
        add more functionality here
         */
    }

    private void registerListener(){
        if(mContext != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            //add more filters here
            mContext.registerReceiver(mScreenReceiver, filter);
        }
    }

    private void unregisterListener(){
        if(mContext != null)
            mContext.unregisterReceiver(mScreenReceiver);
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent){
            action = intent.getAction();
            if(Intent.ACTION_SCREEN_ON.equals(action))
                mScreenStateListener.onScreenOn();
            else if(Intent.ACTION_USER_PRESENT.equals(action))
                mScreenStateListener.onUserPresent();
            /*
            Add more actions here with if-else
             */
        }
    }

    //private String getTime(long millTime){
        //TODO: Define get time function
    //}

    public interface ScreenStateListener {
        public void onScreenOn();

        public void onUserPresent();

    }

}

package com.example.kevin.activitylistener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver broadcastReceiver;
    private TextView tv_main;
    private Button mEndButton;
    private SimpleDateFormat formatter;
    private MessageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEndButton = (Button) findViewById(R.id.end_btn);
        init();
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(broadcastReceiver != null && uploader != null){
                    addPrompt("Disconnected");
                    broadcastReceiver.endObserver();
                    Log.i("test","注销");
                    uploader.sendMsg("Disconnected");
                    uploader.endConnection();
                }
            }
        });

    }


    private void init() {
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main.setText("开始监视：");
        uploader = new MessageUploader();
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if (uploader.checkConnection())
            addPrompt("Connection Successful");
        else addPrompt("Connection failed, local operation only");
        formatter = new SimpleDateFormat("HH:mm:ss");
        broadcastReceiver = new MyBroadcastReceiver(this);
        broadcastReceiver.startObserver(new MyBroadcastReceiver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                addPrompt("点亮屏幕 " + formatter.format(new Date(System.currentTimeMillis())));
                sendInThread("点亮屏幕 " + formatter.format(new Date(System.currentTimeMillis())));
            }

            @Override
            public void onUserPresent() {
                addPrompt("解锁 " + formatter.format(new Date(System.currentTimeMillis())) + "\n");
                sendInThread("解锁 " + formatter.format(new Date(System.currentTimeMillis())));
            }
        });
    }

    public void sendInThread(final String msg){
        new Thread(){
            public void run(){
                uploader.sendMsg(msg);
            }

        }.start();
    }


    public void addPrompt(String s) {
        tv_main.append(s + "\n");
    }
}


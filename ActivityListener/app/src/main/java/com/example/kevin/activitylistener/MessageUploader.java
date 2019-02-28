package com.example.kevin.activitylistener;

import java.io.*;
import java.net.Socket;


public class MessageUploader implements Runnable {

    private Socket clientSocket;
    private boolean isReceivingMsgReady = false;
    private PrintWriter mWriter;
    private Thread t1;

    public MessageUploader(){
        // TODO initialize socket
        if(! isReceivingMsgReady){
            t1 = new Thread(this);
            t1.start();
        }
    }

    public void sendMsg(String msg){
        try {
            mWriter.println(msg);
            mWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String ip = "103.46.128.47"; // change ip here
        int port = 17040; //change portal here

        try {
            isReceivingMsgReady = true;
            clientSocket = new Socket(ip, port);
            clientSocket.setSoTimeout(50000);
            mWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean checkConnection(){
        if(isReceivingMsgReady) {
            return true;
        } else return false;
    }

    public void endConnection(){
        isReceivingMsgReady = false;
        try{
            if(clientSocket != null){
                mWriter.close();
                clientSocket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}

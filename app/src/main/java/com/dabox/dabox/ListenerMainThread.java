package com.dabox.dabox;

import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

public class ListenerMainThread implements Runnable{
    private boolean shouldContinue;
    private int port;
    private int timeOut;

    public ListenerMainThread(){

    }

    public ListenerMainThread(int port, int timeOut){
        this.port=port;
        this.timeOut=timeOut;
    }

    @Override
    public void run() {
        shouldContinue = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeOut);
            while (shouldContinue) {
                Socket socket = serverSocket.accept();
                new ListenerThread(socket).start(); //we gave it away, so it will be handled in a separate thread
            }
        } catch (java.io.IOException e) {
            e.printStackTrace(); // TODO: 2016. 03. 06. handle this
        }
    }

    public void stop(){
        shouldContinue=false;
    }
}

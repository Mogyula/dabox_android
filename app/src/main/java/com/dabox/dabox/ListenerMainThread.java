package com.dabox.dabox;

import java.net.ServerSocket;
import java.net.Socket;

public class ListenerMainThread implements Runnable{
    private boolean shouldContinue;
    private Integer port;

    public ListenerMainThread(Integer port){
        this.port=port;
    }

    @Override
    public void run() {
        shouldContinue = true;
        while (shouldContinue) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(R.integer.listen_timeout);
                Socket socket = serverSocket.accept();
                new ListenerThread(socket).start(); //we gave it away, so it will be handled in a separate thread
            } catch (java.io.IOException e) {
                e.printStackTrace(); // TODO: 2016. 03. 06. handle this
            }
        }
    }

    public void stop(){
        shouldContinue=false;
    }
}

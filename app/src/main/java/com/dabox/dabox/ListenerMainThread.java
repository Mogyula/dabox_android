package com.dabox.dabox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerMainThread implements Runnable {
    private boolean shouldContinue;
    private int port;
    private int timeOut;
    private static Long macAddress;

    public ListenerMainThread(int port, int timeOut) {
        this.port = port;
        this.timeOut = timeOut;

        try {
            setMACAddress();
        }catch (IOException e){
            e.printStackTrace();
        } // TODO: 2016. 03. 15. handle exceptions

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

    public void stop() {
        shouldContinue = false;
    }

    private void setMACAddress() throws IOException {
        FileReader fileReader;
        File file = new File("/sys/class/net/eth0/address");
        if (file.exists()) {
            fileReader = new FileReader("/sys/class/net/eth0/address");
        } else {
            fileReader = new FileReader("/sys/class/net/wlan0/address");
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ListenerMainThread.macAddress = Long.parseLong(bufferedReader.readLine().replace(":", ""),16); //it's gonna be on the first line anyways.
    }

    public static Long getMACAddress(){
        return ListenerMainThread.macAddress;
    }
}
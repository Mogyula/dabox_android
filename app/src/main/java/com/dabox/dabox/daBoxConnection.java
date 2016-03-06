package com.dabox.dabox;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class daBoxConnection implements Runnable{
    private Thread thread;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Integer channel;
    private String serverName;
    private Integer serverPort;
    private Context context;

    public daBoxConnection(String serverName, Integer serverPort, Integer channel, Context context) throws Exception{
        this.context = context;
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.channel = channel;
    }

    @Override
    public void run(){
        try {
            this.socket = new Socket(this.serverName, this.serverPort);
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            String outMessage = "android";
            dataOutputStream.writeUTF(outMessage);
        }catch(Exception e){
            Log.e("sendMessage()", e.toString());
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }
}

package com.dabox.dabox;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class daBoxConnection implements Runnable{
    private Thread thread;
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
        //check if the trigger has been activated, and if so, then send it

        boolean dummy = MainActivity.getTriggerContainer().isActive(channel);

        if (MainActivity.getTriggerContainer().isActive(channel)){
            try {
                Socket socket = new Socket(serverName, serverPort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                byte[] outMessage = BigInteger.valueOf(12).shiftLeft(15 * 8)
                        .add(BigInteger.valueOf(channel).shiftLeft(11 * 8))
                        .toByteArray();
                dataOutputStream.write(outMessage);
                byte[] readBytes = new byte[16];
                dataInputStream.read(readBytes);
                
                if(readBytes[0]==15){
                    //There was an error, handle it.
                    //Maybe we should send it again a few times, display an error message, etc.
                    // TODO: 2016. 03. 16. handle errors here somewhere 
                }
                
                socket.close();
            } catch (Exception e) {
                Log.e("sendMessage()", e.toString());
            }
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }
}

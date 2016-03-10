package com.dabox.dabox;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;

public class ListenerThread implements Runnable{
    //This class will be instantiated every time we encounter a connection
    private Thread thread;
    private Socket socket;
    private byte[] readBytes;

    public ListenerThread(Socket socket){
        this.socket=socket;
        this.readBytes= new byte[16];
    }

    @Override
    public void run(){
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(readBytes);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write(doProcessing(readBytes));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    private byte[] doProcessing(byte[] data){
        return "Hallo, was kostet die Garage?".getBytes();
        /*
        try {
            Log.e("AAAAAAAAAAAAAAAAAAA", new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // TODO: 2016. 03. 06. extract first byte
        byte firstByte = data[0];
        switch (firstByte){
            case 1:
                return msgSendId();
            // TODO: 2016. 03. 06. implement everything...
        }
        */
    }

    private byte[] msgSendId(){
        BigInteger result;
        String MACString;
        try {
            MACString = getMACAddress();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        //here we'll convert that string to byte array
        MACString = MACString.replace(":", "");
        result = BigInteger.valueOf(Long.parseLong(MACString, 16)).shiftLeft(7*8)
            .add(BigInteger.valueOf(2).shiftLeft(15*8))
            .add(BigInteger.valueOf(R.integer.device_id).shiftLeft(3*8));
        return result.toByteArray();
    }

    private String getMACAddress() throws IOException {
        FileReader fileReader;
        File file = new File("/sys/class/net/eth0/address");
        if (file.exists()){
            fileReader = new FileReader("/sys/class/net/eth0/address");
        }else{
            fileReader = new FileReader("/sys/class/net/wlan0/address");
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader.readLine(); //it's gonna be on the first line anyways.
    }
}

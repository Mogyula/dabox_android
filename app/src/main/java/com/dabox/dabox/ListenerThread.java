package com.dabox.dabox;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        byte firstByte = data[0];

        switch (firstByte){
            case 1:
                return sendId();
            case 3:
                return initSetupMode();
            case 4:
                return startExec();
            case 5:
                return setArg(data);
            case 6:
                return activateTrigger(data);
            case 11:
                return handleTrigger(data);
        }
        return BigInteger.valueOf(15).shiftLeft(15 * 8).toByteArray();
    }

    private byte[] activateTrigger(byte[] data){
        Long MACAddress = ListenerMainThread.getMACAddress();

        Integer triggerID = new BigInteger(1, data)
                .shiftRight(11*8)
                .and(BigInteger.valueOf(0xFFFFFFFF))
                .intValue();

        MainActivity.getTriggerContainer().activateTrigger(triggerID);

        return BigInteger.valueOf(10).shiftLeft(15*8)
                .add(BigInteger.valueOf(MACAddress).shiftLeft(7 * 8))
                .add(BigInteger.valueOf(triggerID).shiftLeft(3 * 8))
                .toByteArray();
    }

    private byte[] handleTrigger(byte[] data){
        return BigInteger.valueOf(15).shiftLeft(15*8).toByteArray();
        //// TODO: 2016. 03. 15. implement triggers maybe?
    }

    private byte[] setArg(byte[] data){
        return BigInteger.valueOf(15).shiftLeft(15 * 8).toByteArray();
        //// TODO: 2016. 03. 15. implement arguments maybe?
    }

    private byte[] startExec(){
        Long MACAddress = ListenerMainThread.getMACAddress();

        return BigInteger.valueOf(MACAddress).shiftLeft(7 * 8)
                .add(BigInteger.valueOf(8).shiftLeft(15*8)).toByteArray();
    }

    private byte[] initSetupMode(){
        Long MACAddress = ListenerMainThread.getMACAddress();

        MainActivity.getTriggerContainer().deactivateAll();

        return BigInteger.valueOf(MACAddress).shiftLeft(7 * 8)
                .add(BigInteger.valueOf(7).shiftLeft(15*8)).toByteArray();
    }

    private byte[] sendId(){
        Long MACAddress = ListenerMainThread.getMACAddress();

        //here we'll convert that string to byte array
        return BigInteger.valueOf(MACAddress).shiftLeft(7 * 8)
                .add(BigInteger.valueOf(2).shiftLeft(15 * 8))
                .add(BigInteger.valueOf(R.integer.device_id).shiftLeft(3 * 8))
                .toByteArray(); // TODO: 2016. 03. 12. get device_id correctly
    }
}

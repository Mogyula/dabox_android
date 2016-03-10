package com.dabox.dabox;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class ListenerService extends Service{
    private Thread thread;
    private ListenerMainThread listenerMainThread;

    public ListenerService(){
        super();
    }

    public IBinder onBind(Intent arg){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        int port = intent.getIntExtra("outPort", 22000);
        int timeOut = intent.getIntExtra("timeOut", 10000);
        listenerMainThread = new ListenerMainThread(port, timeOut);
        thread = new Thread(listenerMainThread);
        thread.start();
        super.onStartCommand(intent,flags,startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        listenerMainThread.stop();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO: 2016. 03. 06. handle this 
        }
    }
}

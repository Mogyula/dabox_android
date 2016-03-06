package com.dabox.dabox;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

public class ListenerService extends Service{
    private Thread thread;
    private Integer port;
    private ListenerMainThread listenerMainThread;

    public ListenerService(Integer port){
        this.port=port;
    }

    public IBinder onBind(Intent arg){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        listenerMainThread = new ListenerMainThread(R.integer.inbound_port);
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

package com.fullxays.rpismarthome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.lang.InterruptedException;

import java.net.Socket;

public class ClientSocket extends Service {

    private static final String TAG = "ClientSocket";
    private Intent intent;
    private String ip = "192.168.100.4";
    private int port = 5050;

    private Connection connection;

    public ClientSocket() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"OnCreate Service");
        ip = intent.getStringExtra("ip");
        port = intent.getIntExtra("port",port);
        Log.i(TAG,"onCreate service dsta: " + ip +"   "+ port);

        connection = new Connection(ip,port);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"OnStartService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"OnDestroy Service");
        //
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

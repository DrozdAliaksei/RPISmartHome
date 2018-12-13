package com.fullxays.rpismarthome;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection  {

    private static final String TAG = "Connection";

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public Connection(String ip ,int port) throws IOException {
        Log.i(TAG,"Try to open Client Socket");

        this.socket = new Socket(ip,port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

        try {
            if(in.readObject().equals("Hello, Welcome to RPI")){
                Log.i(TAG,"Hello, Welcome to RPI");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            Log.i(TAG,"closeConnection");
            this.out.writeObject("close");
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.i(TAG,"closeConnection IOException");
        }
    }


    public void sendMassage(String str) throws IOException {
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
            toServer.println(str);
    }

    public String receiveMassage()throws IOException{
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = fromServer.readLine();
        Log.i(TAG, result);
        return result;
    }

}

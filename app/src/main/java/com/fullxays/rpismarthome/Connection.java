package com.fullxays.rpismarthome;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class Connection  {

    private static final String TAG = "Connection";

    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static PrintWriter printWriter;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private String ipAddress;
    private int portNum;

    private static Connection instance;



    private Connection(String ipAddress ,int portNum){
        this.ipAddress = ipAddress;
        this.portNum = portNum;
        Log.i(TAG,"Try to open Client Socket");
        openSocket();
    }

    public static synchronized Connection getInstance(String ipAddress, int portNum){
        if(instance == null){
            instance = new Connection(ipAddress,portNum);
            return instance;
        }
        else
        return instance;
    }

    public Socket openSocket(){
        try  {
            this.socket = new Socket(ipAddress, portNum);
        }catch (IOException ioe){
            Log.i(TAG,"Fuck this connection");
            ioe.printStackTrace();
        }
        return socket;
    }

    public void closeConnection() {
        try {
            out.writeObject("close");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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

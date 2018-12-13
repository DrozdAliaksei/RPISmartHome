package com.fullxays.rpismarthome.Services;

import android.util.Log;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ConnectionService {

    private static final String TAG = "ConnectionService";

    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static PrintWriter printWriter;

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
    private String ip;
    private int port;

    private static Handler handler;

    private static Client client;

    private ConnectionService(){

    }

    public static Client getInstance(String ipAddress, int portNum){
        if(client == null){
            Log.i(TAG,"Creating connection "+ ipAddress + "_" +portNum);
            client = new Client(ipAddress,portNum);
            client.run();
            return client;
        }
        else
            return client;
    }

    /////////////// client thread ////////////////////////////
    private static class Client extends Thread {
        private String ipaddress;
        private int portnum;


        public Client(String ipaddress, int portnum) {
            this.ipaddress = ipaddress;
            this.portnum = portnum;
        }

        @Override
        public void run() {
            super.run();
            connectToServer(ipaddress, portnum);

        }


        public void connectToServer(String ip, int port) {

            try {
                socket = new Socket(InetAddress.getByName(ip), port);
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());
                for (int i = 0; i < 1; i++) {
                    System.out.println((String) in.readObject() + "\n");
                }
                //checkSwitchStatus();
                /*handler.post(new Runnable() {
                    public void run() {
                        connect.setText("Close");
                        changeSwitchesSatte(true);
                    }
                });*/
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        //showErrorsMessages("Unkown host!!");
                        Log.i(TAG,"Unkown host!!");
                    }
                });
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }//end of client class

}

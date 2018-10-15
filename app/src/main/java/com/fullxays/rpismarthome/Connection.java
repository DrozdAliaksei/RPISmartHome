package com.fullxays.rpismarthome;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection {

    private static final String TAG = "Connection";

    private Pattern pattern;
    private Matcher matcher;
    private Handler handler;
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private String ipAddress;
    private int portNum;



    public Connection(String ipAddress,int portNum) {
        this.ipAddress = ipAddress;
        this.portNum = portNum;
        Log.i(TAG,"Try to open Client Socket");
        try (Socket socket = new Socket(ipAddress, portNum)) {

        }
        catch (UnknownHostException ex){
            ex.printStackTrace();
        }
        catch (IOException e){
            try {
                if(e instanceof SocketTimeoutException)
                    throw new SocketTimeoutException();
                else
                    e.printStackTrace();
            }
            catch (SocketTimeoutException ste){
                Log.i(TAG,"Turn off the client by timeout");
            }
        }
    }

    public void sendMessage(String str) throws IOException {
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
        if(str == "BYE"){
            toServer.println(str);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i(TAG,fromServer.readLine());
            socket.close();
        }
        else {
            toServer.println(str);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i(TAG, fromServer.readLine());
        }
    }

    private void closeConnection() {
        try {
            out.writeObject("close");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorsMessages(ex.getMessage());
        }
    }//end of closeConnection

    void showErrorsMessages(String error) {
       // AlertDialog.Builder dialog = new AlertDialog.Builder(AuthorizationActivity.this);
        //dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

}

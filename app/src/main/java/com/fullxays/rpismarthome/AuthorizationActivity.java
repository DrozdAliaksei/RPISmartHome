package com.fullxays.rpismarthome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationActivity extends AppCompatActivity {

    //private static final String TAG = AuthorizationActivity.class.getSimpleName();
    private static final String TAG = "Authorization";

    final String SAVED_IP = "saved_ip";
    final String SAVED_PORT = "saved_port";
    SharedPreferences sPref;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Pattern pattern;
    private Matcher matcher;
    private Handler handler;

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private Button autorization;
    private ImageButton connectingSettings;
    private TextInputLayout login;
    private TextInputLayout password;
    private TextInputEditText editLogin;
    private TextInputEditText editPassword;

    private EditText ipAddress;
    private EditText portNum;

    final Context context = this;

    String ip;
    int port;

    String log;
    String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        handler = new Handler();
        setContentView(R.layout.authorization);

        autorization = findViewById(R.id.autorization);
        connectingSettings = findViewById(R.id.connectionSettings);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);

        autorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!checkIP(ip))
                        throw new UnknownHostException(ip + "is not a valid IP address");
                    if (port > 65535 && port < 0)
                        throw new UnknownHostException(port + "is not a valid port number ");
                    Client client = new Client(ip, port);
                    client.start();
                    Intent intent = new Intent(this,ControlPanel.class);
                    intent.putExtra("ipAddress", ip);
                    intent.putExtra("port", port);
                    startActivity(intent);

                } catch (UnknownHostException e) {
                    showErrorsMessages("Please enter a valid IP !! ");
                } catch (NumberFormatException e) {
                    showErrorsMessages("Please enter valid port number !! ");
                }
            }
        });
        connectingSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Connection Settings dialog opening");
                // get connecting_settings.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View settingsView = li.inflate(R.layout.connecting_settings, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alert dialog builder
                alertDialogBuilder.setView(settingsView);

                ipAddress = settingsView.findViewById(R.id.ip_address);
                portNum = settingsView.findViewById(R.id.port_num);

                loadSettings();

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //saving in memory preferences
                                        saveSettings();
                                        //ToDo : add a Listener to the input method of ip-Adress and port
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Log.i(TAG,"Setting dialog closed");
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        ipAddress = findViewById(R.id.ip_address);
        portNum = findViewById(R.id.port_num);



    }

    void saveSettings() {
        Log.i(TAG,"Saving data");
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_IP, ipAddress.getText().toString());
        ed.putString(SAVED_PORT, portNum.getText().toString());
        ed.commit();
    }

    void loadSettings() {
        Log.i(TAG,"Loading saved before data");
        sPref = getPreferences(MODE_PRIVATE);
        String savedIp = sPref.getString(SAVED_IP, "");
        String savedPort = sPref.getString(SAVED_PORT, "");
        ipAddress.setText(savedIp);
        portNum.setText(savedPort);
        ip = savedIp;
        port = Integer.valueOf(savedPort);
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

    @Override
    protected void onStop() {
        super.onStop();
        closeConnection();
    }


    void showErrorsMessages(String error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AuthorizationActivity.this);
        dialog.setTitle("Error!! ").setMessage(error).setNeutralButton("OK", null).create().show();
    }

    public boolean checkIP(final String ip) {
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /////////////// client thread ////////////////////////////
    private class Client extends Thread {
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

                handler.post(new Runnable() {
                    public void run() {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    public void run() {
                        showErrorsMessages("Unkown host!!");
                    }
                });
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }//end of client class
}

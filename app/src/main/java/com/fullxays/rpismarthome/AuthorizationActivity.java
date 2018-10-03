package com.fullxays.rpismarthome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener  {

    //private static final String TAG = AuthorizationActivity.class.getSimpleName();
    private static final String TAG = "Authorization";

    final String SAVED_IP = "saved_ip";
    final String SAVED_PORT = "saved_port";
    SharedPreferences sPref;


    private Button autorization;
    private ImageButton connectingSettings;
    private TextInputLayout login;
    private TextInputLayout password;
    private TextInputEditText editLogin;
    private TextInputEditText editPassword;

    private EditText ipAddress;
    private EditText portNum;

    final Context context = this;

    String log;
    String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        autorization = findViewById(R.id.autorization);
        connectingSettings = findViewById(R.id.connectionSettings);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);

        autorization.setOnClickListener(this);
        connectingSettings.setOnClickListener(this);

        ipAddress = findViewById(R.id.ip_address);
        portNum = findViewById(R.id.port_num);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connectionSettings:
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
                break;

            case R.id.autorization:
                Log.i(TAG,"Autorization block");
                //send data to RPI, receive answer and enter
                // or show notification that something is incorrect
                log = login.getEditText().getText().toString();
                pass = password.getEditText().getText().toString();
                Toast toast = Toast.makeText(this,log+"   "+pass,Toast.LENGTH_SHORT);
                //toast.show();
                //ToDo : dosn't show error message below(not so good as i wanted)
                //ToDo: add methods send receive data to check

                if(checkServerAddress()==false){
                    Toast toast1 = Toast.makeText(this,"Please enter server ip-address " +
                            "and port number in settings",Toast.LENGTH_LONG);
                    toast1.show();
                    break;
                }
                //showError();
                Log.i(TAG, ipAddress.getText().toString() + "  " + portNum.getText().toString());
                Intent intent = new Intent(this, ControlPanel.class);
                intent.putExtra("ipAddress", ipAddress.getText().toString());
                intent.putExtra("portNum", portNum.getText().toString());
                startActivity(intent);

                break;
        }
    }

    void showError(){
        //if(!isAuthorized()){
        if(login.getEditText().getText().length()==0) {
            Log.i(TAG,"showing error msg");
            login.setError(getString(R.string.login_error));
            editLogin.setError(getString(R.string.login_error));
            password.setError(getString(R.string.password_error));
            editPassword.setError(getString(R.string.password_error));
        }
        else {
            login.setErrorEnabled(false);
            password.setErrorEnabled(false);
        }
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
        }

    boolean checkServerAddress(){
        Log.i(TAG,"Check that info about ip-address and port stored in memory");
        sPref = getPreferences(MODE_PRIVATE);
        if(sPref.getString(SAVED_IP,"").length()==0 || sPref.getString(SAVED_PORT,"").length() == 0)
           return false;
        else return true;
        }

}

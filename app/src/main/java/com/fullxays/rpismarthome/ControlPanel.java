package com.fullxays.rpismarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ControlPanel extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "ControlPanel";

    private Intent data;

    private ImageButton settings;
    private ImageButton addModule;
    private Spinner chooseRoom;
    private Spinner chooseControllerCategoty;
    private ListView controllers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        checkExtraData();

//        settings = findViewById(R.id.settings);
//        settings.setOnClickListener(this);
//        addModule = findViewById(R.id.addModule);
//        addModule.setOnClickListener(this);
//        chooseRoom = findViewById(R.id.chooseRoom);
//        chooseControllerCategoty = findViewById(R.id.chooseControllerCategory);
//        controllers = findViewById(R.id.controllers);
    }

    void checkExtraData(){
        String SAVED_IP ;
        String SAVED_PORT ;

        data = getIntent();
        SAVED_IP = data.getStringExtra("ipAddress");
        SAVED_PORT = data.getStringExtra("portNum");

        Log.i(TAG,SAVED_IP + " " + SAVED_PORT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings :

                break;
            case R.id.addModule:

                break;
        }
    }
}

package com.fullxays.rpismarthome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fullxays.rpismarthome.Services.ServerCommunicationService;
import com.fullxays.rpismarthome.adapter.ControllerAdapter;
import com.fullxays.rpismarthome.controllers.Controller;

import java.net.Socket;
import java.util.List;

public class ControlPanel extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "ControlPanel";

    private Intent data;
    private ServerCommunicationService serverCommunicationService;
    private ImageButton settings;
    private ImageButton addModule;
    private Spinner chooseRoom;
    private Spinner chooseControllerCategoty;

    private RecyclerView recyclerViewControllers;
    private ControllerAdapter controllerAdapter;

    private List<Controller> controllersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        checkExtraData();
       // serverCommunicationService.getPinsStatus();

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);
        addModule = findViewById(R.id.addModule);
        addModule.setOnClickListener(this);
        chooseRoom = findViewById(R.id.chooseRoom);
        chooseControllerCategoty = findViewById(R.id.chooseControllerCategory);

        //initRecycleView();
    }

    private void initRecycleView(){
        recyclerViewControllers = findViewById(R.id.recyclerViewControllers);
        recyclerViewControllers.setLayoutManager(new LinearLayoutManager(this));

         controllerAdapter = new ControllerAdapter();

        new FetchItemTask().execute();
        setupAdapter();
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

    private void setupAdapter(){
        recyclerViewControllers.setAdapter(controllerAdapter);
    }

    private class FetchItemTask extends AsyncTask<Void,Void,List<Controller>> {
        @Override
        protected List<Controller> doInBackground(Void... params) {
            //return new ServerCommunicationService().getPinsStatus();
            return null; //TODO заглушка
        }

        @Override
        protected void onPostExecute(List<Controller> controllers) {
            controllersList = controllers;
            setupAdapter();
        }
    }

}

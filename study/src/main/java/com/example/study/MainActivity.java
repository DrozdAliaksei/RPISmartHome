package com.example.study;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView controllersRecycleView;
    private List<Controller> mItems = new  ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllersRecycleView = findViewById(R.id.contentPanel);
        controllersRecycleView.setLayoutManager(new LinearLayoutManager(this));

        new FetchItemTask().execute();
        setupAdapter();
    }

    private void setupAdapter(){
        controllersRecycleView.setAdapter(new ControllerAdapter(mItems));
    }

    private class ControllerHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private TextView GPIO;
        private Switch status;
        private LinearLayout controller;

        public ControllerHolder(@NonNull View itemView) {
            super(itemView);
            Name = findViewById(R.id.name);
            GPIO = findViewById(R.id.gpio);
            status = findViewById(R.id.status);
            controller = findViewById(R.id.controller);
        }
    }

    private class ControllerAdapter extends RecyclerView.Adapter<ControllerHolder>{
        private List<Controller> controllersItems;

        public ControllerAdapter(List<Controller> items) {
            this.controllersItems = items;
        }

        @NonNull
        @Override
        public ControllerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View v = inflater.inflate(R.layout.controller,viewGroup);
            return new ControllerHolder(v) ;
        }

        @Override
        public void onBindViewHolder(@NonNull ControllerHolder controllerHolder, int i) {
            Controller controller = controllersItems.get(i);

        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class FetchItemTask extends AsyncTask<Void,Void,List<Controller>>{

        @Override
        protected List<Controller> doInBackground(Void... voids) {
            return new CommunicationService().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Controller> items) {
            mItems = items;
            setupAdapter();
        }
    }
}

package com.fullxays.rpismarthome.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.fullxays.rpismarthome.R;
import com.fullxays.rpismarthome.controllers.Controller;

import java.util.ArrayList;
import java.util.List;

public class ControllerAdapter extends RecyclerView.Adapter<ControllerAdapter.ControllerViewHolder> {

    private List<Controller> controllerList = new ArrayList<>();

    class ControllerViewHolder extends RecyclerView.ViewHolder {
        private Switch status;
        private TextView name;
        private Spinner GPIO;

        public ControllerViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.name);
            name = itemView.findViewById(R.id.lightGPIO);
            GPIO = itemView.findViewById(R.id.lightStatus);
        }

        public void bind(Controller controller){
            status.setChecked(controller.isStatus());
            name.setText(controller.getName());
            GPIO.setSelection(controller.getGPIO());
        }
    }

    public void setItems(List<Controller> controllersItems){
        controllerList.addAll(controllersItems);
        notifyAll();
    }

    private void clearItems(){
        controllerList.clear();
        notifyAll();
    }

    @NonNull
    @Override
    public ControllerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.light, viewGroup, false);
        return new ControllerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControllerViewHolder controllerViewHolder, int i) {
        controllerViewHolder.bind(controllerList.get(i))  ;
    }

    @Override
    public int getItemCount() {
        return controllerList.size();
    }

}

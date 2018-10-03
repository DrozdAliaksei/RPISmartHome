package com.fullxays.rpismarthome.controllers;

public class PowerSocket {
    private String name;
    private int gpio;
    private boolean status;

    public PowerSocket(String name, int gpio){
        this.name = name;
        this.gpio = gpio;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public int getGpio() {
        return gpio;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus(){
        return status   ;
    }
}

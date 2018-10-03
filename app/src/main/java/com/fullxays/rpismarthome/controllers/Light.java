package com.fullxays.rpismarthome.controllers;

public class Light {
    private String name;
    private int gpio;
    private boolean status;

    public Light(String name,int gpio){
        this.name = name;
        this.gpio = gpio;
        this.status = false;
    }

    public String getName(){return name;}
    public int getGpio(){return gpio;}
    public void setStatus(boolean i){status = i;}
    public boolean getStatus(){return status;}
}

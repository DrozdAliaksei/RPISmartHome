package com.fullxays.rpismarthome.controllers;

public class DimLight extends Light {
    private int statusValue;
    public DimLight(String name, int gpio) {
        super(name, gpio);
    }


    public void setStatusValue(int i) {
        this.statusValue = i;
    }
    public int getStatusValue(){
        return statusValue;
    }
}

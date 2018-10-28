package com.example.study;

public class Controller {
    public Controller(int ID, String name, String GPIO, boolean status) {
        this.ID = ID;
        this.name = name;
        this.GPIO = GPIO;
        this.status = status;
    }

    public Controller() {
    }

    private int ID;
    private String name;
    private String GPIO;
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGPIO() {
        return GPIO;
    }

    public void setGPIO(String GPIO) {
        this.GPIO = GPIO;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

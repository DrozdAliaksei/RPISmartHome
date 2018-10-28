package com.fullxays.rpismarthome.Services;

import com.fullxays.rpismarthome.Connection;
import com.fullxays.rpismarthome.controllers.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;


public class ServerCommunicationService {

    Connection connection;


    public List<Controller> getPinsStatus(){      //    {1pin    0/1}
        List<Controller> controllers = new ArrayList<>();
        try {
            connection.sendMassage( "getPinsStatus");
            String controllersCount = connection.receiveMassage();
                for (int i = 0 ; i < valueOf(controllersCount); i++){
                    String controllerInfo = connection.receiveMassage();
                    parseItems(controllers,controllerInfo);
                }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return controllers;
    }

    private  void parseItems(List<Controller> controllers, String contrillerInfo){
        String separator = ";";
        String[] info = contrillerInfo.split(separator);
        Controller controller = new Controller();
        controller.setID(Math.toIntExact(valueOf(info[1])));
        controller.setName(info[2]);
        controller.setGPIO(Math.toIntExact(valueOf(info[3])));
        controller.setStatus(Boolean.parseBoolean(info[4]));
        controllers.add(controller);
    }

}

package com.example.mr2.onlineloader;

/**
 * Created by MR2 on 8/15/2016.
 */
public class Attendees {

    private  String name;
    private String DeviceId;

    public Attendees(String name,String DeviceId){

        this.name = name;
        this.DeviceId = DeviceId;
    }

    public String getname(){
        return this.name;
    }
    public String getDeviceId(){
        return this.DeviceId;
    }
}

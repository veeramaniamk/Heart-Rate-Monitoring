package com.saveetha.heartrate.graphview;

import java.util.ArrayList;

public class GraphResponse {
    private String status,message,id,date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<GraphInner> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<GraphInner> details) {
        this.details = details;
    }

    private ArrayList<GraphInner> details;


    public String[] getPulses() {
        return pulses;
    }

    public void setPulses(String[] pulses) {
        this.pulses = pulses;
    }

    private  String[] pulses;
}

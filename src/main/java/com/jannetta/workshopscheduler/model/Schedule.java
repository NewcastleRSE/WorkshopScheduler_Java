package com.jannetta.workshopscheduler.model;

import java.util.Vector;

public class Schedule {

    private String title;
    private String time;
    private String header;
    private Vector<String> schedule;

    public String getTitle() {
        return title;
    }

    public Schedule(String title, String time, String header, Vector<String> schedule) {
        this.title = title;
        this.time = time;
        this.schedule = schedule;
        this.header = header;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Vector getSchedule() {
        return schedule;
    }

    public void setSchedule(Vector schedule) {
        this.schedule = schedule;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

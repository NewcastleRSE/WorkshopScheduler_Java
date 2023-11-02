package com.jannetta.workshopscheduler.model;

import java.util.Vector;

public class Schedule {

    private String title;
    private String time;
    private Vector schedule;

    public String getTitle() {
        return title;
    }

    public Schedule(String title, String time, Vector schedule) {
        this.title = title;
        this.time = time;
        this.schedule = schedule;
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
}

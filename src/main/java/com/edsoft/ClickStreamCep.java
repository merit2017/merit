package com.edsoft;

import java.util.Calendar;

/**
 * Created by edsoft on 06.01.2016.
 */
public class ClickStreamCep {
    private String prev_id;
    private String time;

    public ClickStreamCep(String prev_id) {
        this.prev_id = prev_id;
        this.time = Calendar.getInstance().getTime().toString();
    }

    public ClickStreamCep() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrev_id() {
        return prev_id;
    }

    public void setPrev_id(String prev_id) {
        this.prev_id = prev_id;
    }

    @Override
    public String toString() {
        return prev_id + "\t" + "% 10 indirim kazandınız" + "\t" + time;
    }

}

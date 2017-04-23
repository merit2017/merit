package com.edsoft;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edsoft on 15.12.2015.
 */
public class ClickStream implements java.io.Serializable {
    private String prev_id;
    private String current_id;
    private int clickCount;
    public static int THRESHOLD = 10;
    private boolean assing;
    private List<Long> timeList;

    public ClickStream(String prev_id, String current_id, int clickCount) {
        this.prev_id = prev_id;
        this.current_id = current_id;
        this.clickCount = clickCount;
        this.assing = false;
        timeList = new ArrayList<>();
    }

    public ClickStream() {
    }

    public String getCurrent_id() {
        return current_id;
    }

    public void setCurrent_id(String current_id) {
        this.current_id = current_id;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getPrev_id() {
        return prev_id;
    }

    public void setPrev_id(String prev_id) {
        this.prev_id = prev_id;
    }

    public boolean isAssing() {
        return assing;
    }

    public void setAssing(boolean assing) {
        this.assing = assing;
    }

    public List<Long> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Long> timeList) {
        this.timeList = timeList;
    }

    public void addTime() {
        timeList.add(System.nanoTime());
    }

    @Override
    public String toString() {
        return prev_id + "\t" + current_id + "\t" + clickCount;
    }
}

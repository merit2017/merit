package com.edsoft.iot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edsoft on 24.12.2015.
 */
public class Data implements java.io.Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("value")
    private int value;
    @JsonProperty("sensorType")
    private String sensorType;
    @JsonProperty("timeList")
    private List<Long> timeList;
    public static int THRESHOLD = 10;
    public Data() {
    }

    public Data(String id, String value, String sensorType) {
        this.id = Integer.parseInt(id);
        this.value = Integer.parseInt(value);
        this.sensorType = sensorType;
        timeList = new ArrayList<>();
    }

    @JsonCreator
    public Data(@JsonProperty("id") int id, @JsonProperty("value") int value, @JsonProperty("sensorType") String sensorType, @JsonProperty("timeList") List<Long> timeList) {
        this.id = id;
        this.value = value;
        this.sensorType = sensorType;
        this.timeList = timeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
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
        return getId() + "\t" + getValue() + "\t" + getSensorType();
    }
}

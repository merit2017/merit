package com.edsoft.iot;

import java.beans.PropertyChangeSupport;

/**
 * Created by user on 12/20/16.
 */
public class State {
    public static final int NOTRUN = 0;
    public static final int LOGIN = 1;
    public static final int RUNNING = 2;
    public static final int HALTED = 3;

    private final PropertyChangeSupport changes=new PropertyChangeSupport( this );

    private String name;
    private int    state;

    public static int getNOTRUN() {
        return NOTRUN;
    }

    public static int getLOGIN() {
        return LOGIN;
    }

    public static int getRUNNING() {
        return RUNNING;
    }

    public static int getHALTED() {
        return HALTED;
    }

    public PropertyChangeSupport getChanges() {
        return changes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

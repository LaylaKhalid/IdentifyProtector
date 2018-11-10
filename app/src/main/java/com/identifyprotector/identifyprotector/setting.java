package com.identifyprotector.identifyprotector;

public class setting {
    private String duration;
    private String ActivateAlert;
    private String ID;

    public setting(String duration, String activateAlert, String ID) {
        this.duration = duration;
        ActivateAlert = activateAlert;
        this.ID = ID;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getActivateAlert() {
        return ActivateAlert;
    }

    public void setActivateAlert(String activateAlert) {
        ActivateAlert = activateAlert;
    }

    public String getID() {
        return ID;
    }

    public void setID(String username) {
        this.ID = username;
    }
}

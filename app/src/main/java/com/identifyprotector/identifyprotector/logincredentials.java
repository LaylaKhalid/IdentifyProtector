package com.identifyprotector.identifyprotector;

public class logincredentials {

    private String ID;
    private String Nickname;
    private String ActivateMonitoring;
    private String user;
    private String pass;
    private String appname;


   /* public logincredentials(String nickname) {
        Nickname = nickname;
    }*/

    public logincredentials(String ID, String nickname,String appname, String activateMonitoring, String User, String Pass) {
        this.ID=ID;
        Nickname = nickname;
        this.appname=appname;
        ActivateMonitoring = activateMonitoring;
        user=User;
        pass=Pass;

    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getActivateMonitoring() {
        return ActivateMonitoring;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public void setActivateMonitoring(String activateMonitoring) {
        ActivateMonitoring = activateMonitoring;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

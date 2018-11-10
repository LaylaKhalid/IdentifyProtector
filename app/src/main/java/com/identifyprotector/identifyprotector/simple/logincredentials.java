package com.identifyprotector.identifyprotector.simple;

public class logincredentials {


    private int ID;
    private String Nickname;
    private String ActivateMonitoring;
    private String appname;
    private String user;
    private String pass;
    private String UserID;
    public logincredentials(int ID, String Nickname, String appname, String ActivateMonitoring, String user, String pass, String UserID) {

        this.ID=ID;
        this.Nickname = Nickname;
        this.appname=appname;
        this.ActivateMonitoring=ActivateMonitoring;
        this.user = user;
        this.pass = pass;
        this.UserID=UserID;
    }

    public int getId() {
        return ID;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getUserName() {
        return user;
    }

    public String getPassword() {
        return pass;
    }

    public String getActivateMonitoring()
    {
        return ActivateMonitoring;
    }

    public String ApplicationName(){
        return appname;
    }

    public String UserID(){

        return UserID;
    }
}



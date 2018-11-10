package com.identifyprotector.identifyprotector;

public class test {
    private String user;
    private String IV;

    public test(String user, String IV) {
        this.user = user;
        this.IV = IV;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIV() {
        return IV;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }
}
